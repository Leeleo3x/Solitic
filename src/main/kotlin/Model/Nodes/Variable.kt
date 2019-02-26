package me.leo.project.solidity.Model

import Model.Nodes.AssignableExpression
import Model.Nodes.Node
import me.leo.project.solidity.Model.Types.ArrayType
import org.sosy_lab.java_smt.api.Formula
import kotlin.reflect.KClass
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue


class Variable(val name: String, override val type: Type): AssignableExpression() {

    companion object {
        fun generate(parent: Node, type: KClass<out Type>): Variable {
            parent.variables()
            val (n, t) = parent.variables().filter { type.isInstance(it.second) }.random()
            return Variable(n, t)
        }
    }
}