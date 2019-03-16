package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.PrimitiveType


class Assignment(val variable: Variable, val expression: Expression): Statement() {
    override val scope: Scope? = null

}