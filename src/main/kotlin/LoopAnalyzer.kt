package me.leo.project.solidity

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.misc.Interval


class LoopAnalyzer(private val source: CharStream): SolidityBaseListener() {
    override fun enterForStatement(ctx: SolidityParser.ForStatementContext?) {
        super.enterForStatement(ctx)
        ctx?.apply {
            println(source.getText(Interval(this.start.startIndex, this.stop.stopIndex)))
        }
    }

    override fun enterWhileStatement(ctx: SolidityParser.WhileStatementContext?) {
        super.enterWhileStatement(ctx)
        ctx?.apply {
            println(source.getText(Interval(this.start.startIndex, this.stop.stopIndex)))
        }
    }
}

