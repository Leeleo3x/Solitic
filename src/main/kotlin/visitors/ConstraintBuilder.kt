package me.leo.project.solidity.visitors

import me.leo.project.solidity.SolidityBaseVisitor
import me.leo.project.solidity.SolidityParser.*
import me.leo.project.solidity.model.nodes.*
import me.leo.project.solidity.model.types.IndexType
import me.leo.project.solidity.model.types.PrimitiveType
import me.leo.project.solidity.model.types.Type
import mu.KotlinLogging
import org.antlr.v4.runtime.tree.TerminalNode
import kotlin.reflect.full.primaryConstructor

class ConstraintBuilder(private val symbols: Map<String, Type>): SolidityBaseVisitor<Expression?>() {

    private val freeVariable = HashMap<String, Type>()
    private val logger = KotlinLogging.logger(ConstraintBuilder::class.java.name)


    fun updateTypeRecursive(exp: Expression, type: Type) {

    }

    override fun visitExpression(ctx: ExpressionContext): Expression? {
        when (ctx.childCount) {
            1 -> return visit(ctx.getChild(0))
            4 -> {
                if ((ctx.getChild(1) as? TerminalNode)?.symbol?.text == "[") {
                    visit(ctx.getChild(0))?.let { array ->
                        (array.type as? IndexType)?.let { indexType ->
                            visit(ctx.getChild(2))?.let {index ->
                                if (index.type is PrimitiveType.ANY) {
                                    index.type = indexType.valueType
                                    if (index is Variable) {
                                        freeVariable[index.name] = indexType.valueType
                                    }
                                }
                                else if (index.type != indexType.indexingType) {
                                    logger.error { "Type mismatch." }
                                    return null
                                }
                                return Indexing(array, index)
                            }
                        }
                    }
                }
            }
            3 -> {
                visit(ctx.getChild(0))?.let { left ->
                    visit(ctx.getChild(2))?.let { right ->
                        return BinaryOperation.operators[ctx.getChild(1).text]?.primaryConstructor?.call(left, right)
                    }
                }
            }
        }
        return null
    }

    override fun visitPrimaryExpression(ctx: PrimaryExpressionContext): Expression? {
        ctx.BooleanLiteral()?.let {
            return PrimaryExpression(PrimitiveType.BOOLEAN, ctx.text)
        }
        ctx.HexLiteral()?.let {
            return PrimaryExpression(PrimitiveType.INT, ctx.text)
        }
        ctx.numberLiteral()?.let {
            return PrimaryExpression(PrimitiveType.INT, ctx.text)
        }
        ctx.identifier()?.let {identifier ->
            val name = identifier.text
            freeVariable[name]?.let {
                return Variable(it, name)
            }
            symbols[name]?.let {
                return Variable(it, name)
            }
        }
        return visit(ctx.getChild(0))
    }


    override fun visitTerminal(node: TerminalNode?): Expression? {
        return super.visitTerminal(node)
    }

    override fun visitForAllExpression(ctx: ForAllExpressionContext): Expression? {
        freeVariable[ctx.identifier().text] = PrimitiveType.ANY
        visit(ctx.expression())?.let {
            return ForAllExpression(ctx.identifier().text, it)
        }
        return null
    }

    override fun visitSumExpression(ctx: SumExpressionContext?): Expression? {
        return super.visitSumExpression(ctx)
    }
}

