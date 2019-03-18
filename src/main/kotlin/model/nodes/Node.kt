package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.PrimitiveType
import me.leo.project.solidity.model.types.Type

abstract class Node {
    var parent: Node? = null
    abstract val children: List<Node>
    open val stateVariables: List<String>
        get() = children.map { it.stateVariables }.flatten()

    open val muVariables: List<String>
        get() = children.map { it.muVariables }.flatten()

    fun pathToRoot(): Iterable<Node> {
        return NodeIterable(this)
    }

    abstract val scope: Scope?

    fun containsKey(key: String): Boolean {
        return scope?.symbols?.containsKey(key) ?:false
    }

    fun variables(): List<Pair<String, Type>> {
        return pathToRoot().filter { it.scope != null }
                .map { node -> node.scope?.symbols?.map { it.toPair() } ?: emptyList() }
                .flatten()
    }


    companion object {
        var count = 0
        fun createVariable(type: Type): String {
            return "tmp-$type-${count++}"
        }
    }
}

abstract class Expression: Node() {
    var type: Type = PrimitiveType.ANY
    override val scope: Scope? = null

    open val isMuExpression
        get() = muVariables.isNotEmpty()
}

abstract class AssignableExpression: Expression()

abstract class Statement: Node() {
    override val children = emptyList<Statement>()
}


class NodeIterable(private val node: Node?): Iterable<Node> {
    class NodeIterator(private var current:Node?): Iterator<Node> {
        override fun hasNext(): Boolean {
            return current != null
        }

        override fun next(): Node {
            val tmp = current
            current = current?.parent
            return tmp!!
        }
    }
    override fun iterator(): Iterator<Node> {
        return NodeIterator(node)
    }
}