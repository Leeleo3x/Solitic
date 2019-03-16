package me.leo.project.solidity.solver

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import me.leo.project.solidity.model.types.PrimitiveType
import me.leo.project.solidity.model.types.Type
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

    fun init(name: String, type: Type, data: JsonElement) {
        var variable: Formula? = null
        when (type) {
            PrimitiveType.INT, PrimitiveType.ADDRESS -> {
                variable = integerFormula.makeVariable(name)
                val value = integerFormula.makeNumber(data.asBigInteger)
                prover.addConstraint(integerFormula.equal(variable, value))
            }
//            is MappingType -> {
//                        var arr = arrayFormula.makeArray(name,
//                                FormulaType.IntegerType,
//                                FormulaType.IntegerType)
//                        val dict = data as JsonObject
//                        dict.asJsonObject.keySet().forEach {
//                            val intKey = integerFormula.makeNumber(it)
//                            val intVal = integerFormula.makeNumber(dict[it].asBigInteger)
//                            arr = arrayFormula.store(arr, intKey, intVal)
//
//                        }
//                        variable = arr
//            }
//            is ArrayType -> {
//                var arr = arrayFormula.makeArray(name, FormulaType.IntegerType,
//                        FormulaType.IntegerType)
//                data.asJsonArray.forEachIndexed { index, element ->
//                    val intKey = integerFormula.makeNumber(index.toLong())
//                    val intVal = integerFormula.makeNumber(element.asBigInteger)
//                    arr = arrayFormula.store(arr, intKey, intVal)
//
//                }
//                variable = arr
//            }
        }
        variable?.let { formulaMap[name] = variable }
    }
}