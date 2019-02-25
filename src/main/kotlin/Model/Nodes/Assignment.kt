package Model.Nodes

import me.leo.project.solidity.Model.Variable


sealed class Assignment(val variable: Variable, val expression: Expression): Statement() {
    fun generate() {

    }

}