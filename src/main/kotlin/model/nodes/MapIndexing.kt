package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.MappingType

class MapIndexing(val mapping: Variable, val indexing: Expression): Expression() {
    override val type = (mapping.type as MappingType).valueType

    companion object {
        fun generate(parent: Node): MapIndexing? {
            val mapping = Variable.generate(parent, MappingType::class)
            val expression = Expression.generate(parent)
            return null
        }
    }
}