package me.leo.project.solidity.Model.Nodes

import Model.Nodes.Node
import Model.Nodes.Statement


class BlockStatement(val statements: MutableList<Statement> = mutableListOf()): Statement() {
    override val scope = Scope()
}
