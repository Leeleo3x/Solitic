package me.leo.project.solidity.synthesis

import com.google.gson.JsonObject
import me.leo.project.solidity.Model.Interpretor.Interpreter

class Synthesizer(val initState: State, val post: JsonObject) {
    fun run() {
        val program = initState.program
        val interpreter = Interpreter(initState)
        while (true) {
            Generator.generateStatement(program)?.let {
                program.statements.add(it)
            }
            interpreter.verify(post)
        }
    }
}