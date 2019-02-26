package me.leo.project.solidity

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.leo.project.solidity.Model.Nodes.BlockStatement
import me.leo.project.solidity.Model.PrimitiveType
import me.leo.project.solidity.solver.Solver
import me.leo.project.solidity.synthesis.State
import me.leo.project.solidity.synthesis.Synthesizer
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.FileReader



fun prepareInitState(src: String, constraints: String) {
    val stream = CharStreams.fromFileName(src)
    val lexer = SolidityLexer(stream)
    val parser = SolidityParser(CommonTokenStream(lexer))
    val solver = Solver()

    val program = BlockStatement()
    val collector = VariableCollector(program)
    collector.visit(parser.sourceUnit())
    program.scope.symbols["msg.sender"] = PrimitiveType.ADDRESS

    val json = Gson().fromJson(FileReader(constraints), JsonObject::class.java)

    val pre = json["pre"].asJsonObject
    program.scope.symbols.forEach {
        solver.init(it.key, it.value, pre[it.key])
    }
    val state = State(solver, program)
    val synthesizer = Synthesizer(state, json["post"].asJsonObject)
    synthesizer.run()
}

fun main(args: Array<String>) {
    prepareInitState(args[1], args[0])

}
