package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.IndexType.*


class Indexing(val array: Expression, val indexing: Expression): AssignableExpression() {
    init {
        type = (array.type as ArrayType).valueType
    }
}