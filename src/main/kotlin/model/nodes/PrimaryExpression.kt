package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.PrimitiveType


class PrimaryExpression(type: PrimitiveType, val value: String): Expression() {
    init {
        this.type = type
    }
}