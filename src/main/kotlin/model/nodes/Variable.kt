package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.Type
import kotlin.reflect.KClass


class Variable(val name: String, override val type: Type): AssignableExpression() {

    companion object {
        fun generate(parent: Node, type: KClass<out Type>): Variable {
            parent.variables()
            val (n, t) = parent.variables().filter { type.isInstance(it.second) }.random()
            return Variable(n, t)
        }
    }
}