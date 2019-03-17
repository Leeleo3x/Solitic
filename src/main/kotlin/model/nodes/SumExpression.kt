package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.PrimitiveType

class SumExpression(val variable: String, val value: Expression, val predicate: Expression): Expression() {
    init {
        type = PrimitiveType.INT
    }

    override val children = listOf(value, predicate)
}
