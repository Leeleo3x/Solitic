package me.leo.project.solidity.synthesis

import me.leo.project.solidity.model.nodes.Assignment
import me.leo.project.solidity.model.nodes.Statement
import me.leo.project.solidity.model.types.Type
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.functions


object Generator {
//    private val statements = listOf(ForEachStatement::class.companionObject, Assignment::class.companionObject)
    private val statements = listOf(Assignment::class.companionObject)


    fun generateStatement(parent: Statement): Statement? {
        val statement = statements.random()
        val func = statement?.functions?.find { it.name ==  "generate" }
        (func?.call(statement.objectInstance, parent) as? Statement)?.let {
            it.parent = parent
            return it
        }
        return null
    }


}