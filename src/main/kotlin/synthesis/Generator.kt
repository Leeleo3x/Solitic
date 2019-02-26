package me.leo.project.solidity.synthesis

import Model.Nodes.Assignment
import Model.Nodes.ForEachStatement
import Model.Nodes.Node
import Model.Nodes.Statement
import me.leo.project.solidity.Model.Type
import me.leo.project.solidity.Model.Types.ArrayType
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


    fun generateVariable(variables: List<Pair<String, Type>>): Pair<String, Type>? {
        if (variables.isEmpty()) { return null }
        val (name, type) = variables.random()
        return Pair(name, type)
    }

    fun generateArrayVariable(variables: List<Pair<String, Type>>): Pair<String, Type>? {
        return generateVariable(variables.filter { it.second is ArrayType })
    }

}