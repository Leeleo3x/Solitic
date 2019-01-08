package me.leo.project.solidity

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.io.File

fun main(args: Array<String>) {
    File(args[0]).walkTopDown().filter { it.isFile }.forEach {
        val inputStream = CharStreams.fromFileName(it.name)
        val lexer = SolidityLexer(inputStream)
        val tokens = CommonTokenStream(lexer)
        val parser = SolidityParser(tokens)
        val walker = ParseTreeWalker()
        walker.walk(LoopAnalyzer(inputStream), parser.sourceUnit())
    }
}
