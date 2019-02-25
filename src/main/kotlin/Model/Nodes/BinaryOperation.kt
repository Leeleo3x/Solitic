package Model.Nodes

import me.leo.project.solidity.Model.PrimitiveType


enum class BinaryOperator {
    ADD, SUB, MUL, DIV, GT, GE, LT, LE
}

class BinaryOperation(val operator: BinaryOperator,
                      val left: Expression,
                      val right: Expression): Expression() {
    override val type = PrimitiveType.INT
}