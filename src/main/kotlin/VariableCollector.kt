package me.leo.project.solidity

import Model.Nodes.Node
import me.leo.project.solidity.Model.Nodes.Program
import me.leo.project.solidity.Model.PrimitiveType
import me.leo.project.solidity.Model.Type
import me.leo.project.solidity.Model.Types.ArrayType
import me.leo.project.solidity.Model.Types.MappingType
import me.leo.project.solidity.Model.Variable
import me.leo.project.solidity.SolidityParser.*
import org.antlr.v4.runtime.tree.TerminalNode


class VariableCollector(val program: Program): SolidityBaseVisitor<Type?>() {

    fun createVariable(name: String, type: Type) {
        program.scope.symbols[name] = type
    }

    override fun visitVariableDeclaration(ctx: VariableDeclarationContext): Type? {
        val name = ctx.identifier().text
        visitTypeName(ctx.typeName())?.let {
            createVariable(name, it)
        }
        return null
    }

    override fun visitStateVariableDeclaration(ctx: StateVariableDeclarationContext): Type? {
        val name = ctx.identifier().text
        visitTypeName(ctx.typeName())?.let {
            createVariable(name, it)
        }
        return null
    }


    override fun visitFunctionDefinition(ctx: FunctionDefinitionContext): Type? {
        for (parameter in ctx.parameterList().parameter()) {
            val name = parameter.identifier().text
            visitTypeName(parameter.typeName())?.let {
                createVariable(name, it)
            }
        }
        return null
    }


    override fun visitTypeName(ctx: TypeNameContext): Type? {
        when (ctx.childCount) {
            1 -> return visit(ctx.getChild(0))

            2 -> return PrimitiveType.ADDRESS
            else -> {
                visit(ctx.getChild(0))?.let {
                    return ArrayType(it)
                }
            }
        }
        return null
    }

    override fun visitElementaryTypeName(ctx: ElementaryTypeNameContext): Type? {
        val typeNode = ctx.getChild(0) as TerminalNode
        return when (typeNode.symbol.type) {
            SolidityParser.Uint,
            SolidityParser.Int -> PrimitiveType.INT
            else -> PrimitiveType.ADDRESS
        }
    }


    override fun visitMapping(ctx: MappingContext): Type? {
        visitElementaryTypeName(ctx.elementaryTypeName())?.let {
            indexType ->
            visitTypeName(ctx.typeName())?.let {
                return MappingType(indexType, it)
            }
        }
        return null
    }
}

