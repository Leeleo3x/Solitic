package me.leo.project.solidity


import com.google.gson.Gson
import com.google.gson.JsonObject
import me.leo.project.solidity.solver.Solver
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.io.FileReader


fun prepareContext(src: String, constraints: String)  {
    val stream = CharStreams.fromFileName(src)
    val lexer = SolidityLexer(stream)
    val parser = SolidityParser(CommonTokenStream(lexer))
    val collector = VariableCollector()
    ParseTreeWalker().walk(collector, parser.sourceUnit())
    val identifiers = collector.identifiers
    val solver = Solver()

    val json = Gson().fromJson(FileReader(constraints), JsonObject::class.java)
    val pre = json["pre"].asJsonObject
    pre.keySet().forEach {key ->
        identifiers[key]?.let {
            solver.create(key, it, pre[key])
        }
    }
    val post = json["post"].asJsonObject
}

fun main(args: Array<String>) {
    val property = System.getProperty("java.library.path")
    print(property)
    prepareContext(args[1], args[0])
}
