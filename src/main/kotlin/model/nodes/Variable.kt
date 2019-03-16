package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.Type
import kotlin.reflect.KClass


class Variable(type: Type, val name: String): AssignableExpression() {
    init {
        this.type = type
    }
}