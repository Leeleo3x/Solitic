package me.leo.project.solidity


import Model.Nodes.Statement
import com.google.gson.Gson
import com.google.gson.JsonObject
import me.leo.project.solidity.Model.Nodes.Program
import me.leo.project.solidity.Model.PrimitiveType
import me.leo.project.solidity.solver.Solver
import me.leo.project.solidity.synthesis.Generator
import me.leo.project.solidity.synthesis.State
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.io.FileReader



fun prepareContext(src: String, constraints: String): Program {
    val stream = CharStreams.fromFileName(src)
    val lexer = SolidityLexer(stream)
    val parser = SolidityParser(CommonTokenStream(lexer))
    val solver = Solver()

    val program = Program()

    val collector = VariableCollector(program)
    collector.visit(parser.sourceUnit())
    program.scope.symbols["msg.sender"] = PrimitiveType.ADDRESS

    val json = Gson().fromJson(FileReader(constraints), JsonObject::class.java)

    val pre = json["pre"].asJsonObject
//    pre.keySet().forEach {key ->
//        identifiers[key]?.let {
//            solver.create(key, it, pre[key])
//        }
//    }
//    return Context(solver, json["post"].asJsonObject)
    return program
}

fun main(args: Array<String>) {
    val program = prepareContext(args[1], args[0])

    Generator.generateStatement(program)
}
