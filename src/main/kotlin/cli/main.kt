package me.leo.project.solidity.cli

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.leo.project.solidity.*
import me.leo.project.solidity.model.nodes.BlockStatement
import me.leo.project.solidity.model.types.PrimitiveType
import me.leo.project.solidity.visitors.ConstraintBuilder
import me.leo.project.solidity.visitors.VariableCollector
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.FileReader



fun prepareInitState(src: String, constraints: String) {
    val stream = CharStreams.fromFileName(src)
    val lexer = SolidityLexer(stream)
    val parser = SolidityParser(CommonTokenStream(lexer))
    val source = parser.sourceUnit()

    val collector = VariableCollector()
    collector.visit(source)
    val builder = ConstraintBuilder(collector.symbols)
    val constraints = builder.visit(source)
    print(constraints)
}

fun parseStandard(src: String) {
//    val stream = CharStreams.fromFileName(src)
//    val lexer = StandardLexer(stream)
//    val parser = StandardParser(CommonTokenStream(lexer))
//    val standard = parser.standardDefinition()
//    print(standard)
}

fun main(args: Array<String>) {
    prepareInitState(args[0], args[0])
}
