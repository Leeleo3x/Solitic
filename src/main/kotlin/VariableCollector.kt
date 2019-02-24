package me.leo.project.solidity

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.misc.Interval
import org.antlr.v4.runtime.tree.TerminalNode
import me.leo.project.solidity.SolidityParser.*


class VariableCollector: SolidityBaseListener() {
    val identifiers = HashMap<String, TypeNameContext>()
    override fun enterVariableDeclaration(ctx: VariableDeclarationContext) {
        super.enterVariableDeclaration(ctx)
        identifiers[ctx.identifier().Identifier().text] = ctx.typeName()
    }

    override fun enterStateVariableDeclaration(ctx: StateVariableDeclarationContext) {
        super.enterStateVariableDeclaration(ctx)
        identifiers[ctx.identifier().Identifier().text] = ctx.typeName()
    }


    override fun enterFunctionDefinition(ctx: FunctionDefinitionContext) {
        super.enterFunctionDefinition(ctx)
        for (parameter in ctx.parameterList().parameter()) {
            identifiers[parameter.identifier().Identifier().text] = parameter.typeName()
        }
    }
}

