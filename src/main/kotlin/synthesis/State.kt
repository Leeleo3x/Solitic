package me.leo.project.solidity.synthesis

import com.microsoft.z3.Solver
import me.leo.project.solidity.Context
import me.leo.project.solidity.Model.Nodes.Program

class State(val solver: Solver, val program: Program) {
}