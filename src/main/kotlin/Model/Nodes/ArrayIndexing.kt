package Model.Nodes

import me.leo.project.solidity.Model.Types.ArrayType


class ArrayIndexing(val array: Expression, val indexing: Expression): AssignableExpression() {
    override val type = (array.type as ArrayType).subtype
}