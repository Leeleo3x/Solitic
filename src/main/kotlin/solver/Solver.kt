package me.leo.project.solidity.solver

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import me.leo.project.solidity.SolidityParser
import me.leo.project.solidity.SolidityParser.*
import org.antlr.v4.runtime.tree.TerminalNode
import org.sosy_lab.common.ShutdownNotifier
import org.sosy_lab.common.configuration.Configuration
import org.sosy_lab.common.log.BasicLogManager
import org.sosy_lab.java_smt.SolverContextFactory
import org.sosy_lab.java_smt.api.*

class Solver {
    val shutdown = ShutdownNotifier.createDummy()
    val solverConfig = Configuration.defaultConfiguration()
    val context = SolverContextFactory.createSolverContext(
            solverConfig,
            BasicLogManager.create(solverConfig),
            shutdown,
            SolverContextFactory.Solvers.SMTINTERPOL
    )

    val integerFormula = context.formulaManager.integerFormulaManager
    val arrayFormula = context.formulaManager.arrayFormulaManager
    val prover = context.newProverEnvironment(SolverContext.ProverOptions.GENERATE_MODELS)

    val formulaMap = HashMap<String, Formula>()
    fun create(name: String, type: TypeNameContext, data: JsonElement) {
        var variable: Formula? = null
        when (type.childCount) {
            1 -> {
                val child = type.getChild(0)
                when (child) {
                    is ElementaryTypeNameContext -> {
                        val typeNode = child.getChild(0) as TerminalNode
                        when (typeNode.symbol.type) {
                            SolidityParser.Uint,
                            SolidityParser.Int -> {
                                variable = integerFormula.makeVariable(name)
                                val value = integerFormula.makeNumber(data.asBigInteger)
                                prover.addConstraint(integerFormula.equal(variable, value))
                            }
                            else -> {
                                if (typeNode.text == "address") {
                                    variable = integerFormula.makeVariable(name)
                                    val value = integerFormula.makeNumber(data.asBigInteger)
                                    prover.addConstraint(integerFormula.equal(variable, value))
                                }
                            }
                        }
                    }
                    is MappingContext -> {
                        var arr = arrayFormula.makeArray(name,
                                FormulaType.IntegerType,
                                FormulaType.IntegerType)
                        val dict = data as JsonObject
                        dict.asJsonObject.keySet().forEach {
                            val intKey = integerFormula.makeNumber(it)
                            val intVal = integerFormula.makeNumber(dict[it].asBigInteger)
                            arr = arrayFormula.store(arr, intKey, intVal)

                        }
                        variable = arr
                    }
                }
            }
            2 ->
                integerFormula.makeVariable(name)
            else -> {
                var arr = arrayFormula.makeArray(name, FormulaType.IntegerType,
                        FormulaType.IntegerType)
                data.asJsonArray.forEachIndexed { index, element ->
                    val intKey = integerFormula.makeNumber(index.toLong())
                    val intVal = integerFormula.makeNumber(element.asBigInteger)
                    arr = arrayFormula.store(arr, intKey, intVal)

                }
                variable = arr
            }
        }
        variable?.let { formulaMap[name] = variable }
    }
}