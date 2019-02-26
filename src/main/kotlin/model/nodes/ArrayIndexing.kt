package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.ArrayType


class ArrayIndexing(val array: Expression, val indexing: Expression): AssignableExpression() {
    override val type = (array.type as ArrayType).subtype

    companion object {
        fun generate(parent: Node): ArrayIndexing? {
            return null
        }
    }
}