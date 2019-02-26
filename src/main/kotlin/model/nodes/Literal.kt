package me.leo.project.solidity.model.nodes

import me.leo.project.solidity.model.types.PrimitiveType


class Literal(override val type: PrimitiveType, val value: Int): Expression() {
}