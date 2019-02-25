package me.leo.project.solidity.Model.Nodes

import Model.Nodes.Statement

class Program: Statement() {
    override val scope = Scope()
}