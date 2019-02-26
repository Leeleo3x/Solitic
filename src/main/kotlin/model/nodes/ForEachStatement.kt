package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.ArrayType

class ForEachStatement(val condition: Expression, val body: BlockStatement): Statement() {
    override val scope = Scope()
    val element: String

    init {
        val type = (condition.type as ArrayType).subtype
        element = createVariable(type)
        scope.symbols[element] = type
    }

    companion object {
        fun generate(parent: Statement): ForEachStatement? {
            Variable.generate(parent, ArrayType::class)?.let { variable ->
                val blockStatement = BlockStatement()
                val foreach = ForEachStatement(variable, blockStatement)
                blockStatement.parent = foreach
                variable.parent = foreach
                return foreach
            }
            return null
        }
    }
}