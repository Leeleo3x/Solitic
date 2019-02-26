package me.leo.project.solidity.model.interpreter

import com.google.gson.JsonObject
import me.leo.project.solidity.model.nodes.*
import me.leo.project.solidity.model.nodes.BinaryOperation.*
import me.leo.project.solidity.model.types.PrimitiveType
import me.leo.project.solidity.synthesis.State
import org.sosy_lab.java_smt.api.Formula
import org.sosy_lab.java_smt.api.NumeralFormula.*

class Interpreter(val state: State) {
    private val solver = state.solver
    private val program = state.program

    fun Expression.visit(): Formula? {
        return when(this) {
            is Variable -> solver.formulaMap[this.name]
            is BinaryOperation -> {
                val left = this.left.visit() as IntegerFormula
                val right = this.right.visit() as IntegerFormula
                when(this) {
                    is AddOperation -> solver.integerFormula.add(left, right)
                    is SubOperation -> solver.integerFormula.subtract(left, right)
                    is MulOperation -> solver.integerFormula.multiply(left, right)
                    is DivOperation -> solver.integerFormula.divide(left, right)
                }
            }
            else -> return null
        }
    }


    fun Node.visit() {
        when(this) {
            is Assignment -> {
                val left = this.variable.name
                this.expression.visit()?.let {
                    solver.formulaMap[left] = it
                }
            }
            is BlockStatement -> {
                this.statements.forEach { it.visit() }
            }
        }
    }

    fun verify(post: JsonObject): Boolean {
        program.visit()
        solver.prover.push()
        post.keySet().forEach {
            val type = program.scope.symbols[it]
            when (type) {
                PrimitiveType.INT -> {
                    val result = solver.integerFormula.makeNumber(post[it].asBigInteger)
                    (solver.formulaMap[it] as? IntegerFormula)?.let { formula ->
                        val constraint = solver.integerFormula.equal(result, formula)
                        solver.prover.addConstraint(constraint)
                    }
                }
            }
        }
        return solver.prover.isUnsat
    }

}