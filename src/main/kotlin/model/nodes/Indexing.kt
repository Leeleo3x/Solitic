package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.IndexType


class Indexing(val variable: Expression, val indexing: Expression): AssignableExpression() {
    init {
        type = (variable.type as IndexType).valueType
    }

    override val children: List<Node>
        get() = listOf(variable, indexing)
}