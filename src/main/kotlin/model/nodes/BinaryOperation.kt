package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.PrimitiveType


sealed class BinaryOperation(val left: Expression, val right: Expression): Expression() {
    override val children = listOf(left, right)

    sealed class ArithmeticOperation(left: Expression, right: Expression): BinaryOperation(left, right) {
        class AddOperation(left: Expression, right: Expression): ArithmeticOperation(left, right)
        class SubOperation(left: Expression, right: Expression): ArithmeticOperation(left, right)
        class MulOperation(left: Expression, right: Expression): ArithmeticOperation(left, right)
        class DivOperation(left: Expression, right: Expression): ArithmeticOperation(left, right)

        init {
            type = PrimitiveType.INT
        }

    }


    sealed class ComparisonOperation(left: Expression, right: Expression): BinaryOperation(left, right) {
        class GtOperation(left: Expression, right: Expression): ComparisonOperation(left, right)
        class GeOperation(left: Expression, right: Expression): ComparisonOperation(left, right)
        class LtOperation(left: Expression, right: Expression): ComparisonOperation(left, right)
        class LeOperation(left: Expression, right: Expression): ComparisonOperation(left, right)
        class EqOperation(left: Expression, right: Expression): ComparisonOperation(left, right)

        init {
            type = PrimitiveType.BOOLEAN
        }
    }

    companion object {
        val operators = hashMapOf(
                "+" to ArithmeticOperation.AddOperation::class,
                "-" to ArithmeticOperation.SubOperation::class,
                "*" to ArithmeticOperation.MulOperation::class,
                "/" to ArithmeticOperation.DivOperation::class,
                ">" to ComparisonOperation.GtOperation::class,
                ">=" to ComparisonOperation.GeOperation::class,
                "<" to ComparisonOperation.LtOperation::class,
                "<=" to ComparisonOperation.LeOperation::class,
                "==" to ComparisonOperation.EqOperation::class
        )
    }
}