package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.PrimitiveType


class Assignment(val variable: Variable, val expression: Expression): Statement() {
    override val scope: Scope? = null

    companion object {
        fun generate(parent: Node): Assignment? {
            Variable.generate(parent, PrimitiveType.INT::class)?.let { left ->
                Expression.generate(parent)?.let { right ->
                    val assign = Assignment(left, right)
                    left.parent = assign
                    right.parent = assign
                    return assign
                }

            }
            return null
        }
    }
}