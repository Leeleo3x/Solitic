package Model.Nodes

import me.leo.project.solidity.Model.Nodes.Scope
import me.leo.project.solidity.Model.PrimitiveType
import me.leo.project.solidity.Model.Variable


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