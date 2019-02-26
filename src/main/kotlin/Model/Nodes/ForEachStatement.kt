package Model.Nodes

import me.leo.project.solidity.Model.Nodes.BlockStatement
import me.leo.project.solidity.Model.Nodes.Scope
import me.leo.project.solidity.Model.Types.ArrayType
import me.leo.project.solidity.Model.Variable
import me.leo.project.solidity.synthesis.Generator

class ForEachStatement(val condition: Expression, val body: BlockStatement): Statement() {
    override val scope = Scope()
    val element: String

    init {
        val type = (condition.type as ArrayType).subtype
        element = createVariable(type)
        scope.symbols[element] = type
    }

    companion object {
        fun generate(parent: Statement): ForEachStatement {
            val variable = Variable.generate(parent, ArrayType::class)
            val blockStatement = BlockStatement()
            val foreach = ForEachStatement(variable, blockStatement)
            blockStatement.parent = foreach
            variable.parent = foreach
            return foreach
        }
    }
}