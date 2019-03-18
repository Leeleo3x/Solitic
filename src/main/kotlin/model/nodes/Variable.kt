package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.Type
import kotlin.reflect.KClass


class Variable(type: Type, val isStateVariable: Boolean, val name: String): AssignableExpression() {
    init {
        this.type = type
    }
    override val stateVariables = if (isStateVariable) listOf(name) else emptyList()
    override val muVariables = if (isStateVariable) emptyList() else listOf(name)
    override val children
        get() = emptyList<Node>()
}