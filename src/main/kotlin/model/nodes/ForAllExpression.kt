package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.PrimitiveType


class ForAllExpression(val variable: String, val constraint: Expression): Expression() {
    init {
        type = PrimitiveType.BOOLEAN
    }
    override val children = listOf(constraint)
}

