package me.leo.project.solidity.synthesis

import Model.Nodes.Assignment
import Model.Nodes.ForEachStatement
import Model.Nodes.Node
import Model.Nodes.Statement
import me.leo.project.solidity.Model.PrimitiveType.*
import me.leo.project.solidity.Model.Type
import me.leo.project.solidity.Model.Variable
import javax.lang.model.type.ArrayType
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.functions


object Generator {
    private val statements = listOf(ForEachStatement::class.companionObject, Assignment::class.companionObject)


    fun generateStatement(parent: Statement): Node? {
//        val statement = statements.random()
        val statement = statements[0]
        val func = statement?.functions?.find { it.name ==  "generate" }
        return func?.call(statement.objectInstance, parent) as? Node
    }


    fun generateVariable(variables: List<Pair<String, Type>>): Pair<String, Type>? {
        if (variables.isEmpty()) { return null }
        val (name, type) = variables.random()
        return Pair(name, type)
    }

    fun generateArrayVariable(variables: List<Pair<String, Type>>): Pair<String, Type>? {
        val vars = variables.filter { it.second is ArrayType }
        return generateVariable(variables.filter { it.second is ArrayType })
    }

}