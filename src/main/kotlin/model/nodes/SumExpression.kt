package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.PrimitiveType

class SumExpression(val variable: String, val value: Expression, val predicate: Expression): Expression() {
    init {
        type = PrimitiveType.INT
    }
    val stateVariable = createVariable(PrimitiveType.ANY)

    override val children = listOf(value, predicate)

    override val isMuExpression
        get() = false
}
