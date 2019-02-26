package me.leo.project.solidity.Model

interface Type {
}


sealed class PrimitiveType: Type {
    object INT: PrimitiveType()
    object ADDRESS: PrimitiveType()
    object ANY: PrimitiveType()
}

