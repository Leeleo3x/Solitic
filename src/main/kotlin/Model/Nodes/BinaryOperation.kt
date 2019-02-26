package Model.Nodes

import me.leo.project.solidity.Model.PrimitiveType
import me.leo.project.solidity.Model.Variable
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor


sealed class BinaryOperation(val left: Expression, val right: Expression): Expression() {
    override val type = PrimitiveType.INT

    class AddOperation(left: Expression, right: Expression):BinaryOperation(left, right)
    class SubOperation(left: Expression, right: Expression):BinaryOperation(left, right)
    class MulOperation(left: Expression, right: Expression):BinaryOperation(left, right)
    class DivOperation(left: Expression, right: Expression):BinaryOperation(left, right)
//    class GtOperation(left: Expression, right: Expression):BinaryOperation(left, right)
//    class GeOperation(left: Expression, right: Expression):BinaryOperation(left, right)
//    class LtOperation(left: Expression, right: Expression):BinaryOperation(left, right)
//    class LeOperation(left: Expression, right: Expression):BinaryOperation(left, right)

    companion object {
        fun generate(parent: Node): BinaryOperation? {
            val op = BinaryOperation::class.sealedSubclasses.random()
            val constructor = op.primaryConstructor
            val left = Variable.generate(parent, PrimitiveType.INT::class)
            val right = Variable.generate(parent, PrimitiveType.INT::class)
            val operation = constructor?.call(left, right)
            left.parent = operation
            right.parent = operation
            return operation
        }
    }
}