package me.leo.project.solidity.model.types

interface Type {
}


sealed class PrimitiveType: Type {
    object INT: PrimitiveType()
    object ADDRESS: PrimitiveType()
    object ANY: PrimitiveType()
    object BOOLEAN: PrimitiveType()
}

sealed class IndexType(val indexingType: Type, val valueType: Type): Type {
    class MappingType(indexingType: Type, valueType: Type): IndexType(indexingType, valueType)
    class ArrayType(valueType: Type): IndexType(PrimitiveType.INT, valueType)
}

