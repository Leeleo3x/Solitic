package me.leo.project.solidity.model.nodes


class BlockStatement(val statements: MutableList<Statement> = mutableListOf()): Statement() {
    override val scope = Scope()
}
