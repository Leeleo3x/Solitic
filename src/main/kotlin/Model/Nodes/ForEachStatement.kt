package Model.Nodes

import me.leo.project.solidity.Model.Nodes.BlockStatement
import me.leo.project.solidity.Model.Nodes.Scope
import me.leo.project.solidity.Model.Types.ArrayType
import me.leo.project.solidity.Model.Variable
import me.leo.project.solidity.synthesis.Generator

class ForEachStatement(val condition: Expression, val body: BlockStatement): Statement() {
    override val scope = Scope()

    companion object {
        fun generate(parent: Statement): ForEachStatement? {
            Generator.generateArrayVariable(parent.variables())?.let {
                val blockStatment = BlockStatement(mutableListOf())
                val variable = Variable(it.first, it.second)
                val foreach = ForEachStatement(variable, blockStatment)
                blockStatment.parent = foreach
                variable.parent = foreach
                val elementType = (it.second as ArrayType).subtype
                foreach.scope.symbols[createVariable(elementType)] = elementType
                return foreach
            }
            return null
        }
    }
}