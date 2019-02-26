package me.leo.project.solidity.synthesis

import me.leo.project.solidity.Model.Nodes.BlockStatement
import me.leo.project.solidity.solver.Solver

class State(val solver: Solver, val program: BlockStatement) {
}