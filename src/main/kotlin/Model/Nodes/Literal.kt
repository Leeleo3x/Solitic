package Model.Nodes

import me.leo.project.solidity.Model.PrimitiveType


class Literal(override val type: PrimitiveType, val value: Int): Expression() {
}