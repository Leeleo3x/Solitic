package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.PrimitiveType


class Assignment(val variable: Variable, val expression: Expression): Statement() {
    override val scope: Scope? = null

    companion object {
        fun generate(parent: Node): Assignment? {
            val left = Variable.generate(parent, PrimitiveType.INT::class)
            Expression.generate(parent)?.let {
                val assign = Assignment(left, it)
                left.parent = assign
                it.parent = assign
                return assign
            }
            return null
        }
    }
}