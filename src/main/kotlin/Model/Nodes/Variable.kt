package me.leo.project.solidity.Model

import Model.Nodes.AssignableExpression
import Model.Nodes.Node
import org.sosy_lab.java_smt.api.Formula


class Variable(val name: String, override val type: Type): AssignableExpression() {
}