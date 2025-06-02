package com.example.calculator.models

sealed class OperationType {

    sealed class UnaryOperationType : OperationType() {
        data object Ceil : UnaryOperationType()
        data object Floor : UnaryOperationType()
        data object AbsoluteValue : UnaryOperationType()
        data object Sqrt: UnaryOperationType()
    }

    sealed class BinaryOperationType : OperationType() {
        data object Addition : BinaryOperationType()
        data object Subtraction : BinaryOperationType()
        data object Multiplication : BinaryOperationType()
        data object Division : BinaryOperationType()
        data object DivisionInt: BinaryOperationType()
        data object Modulo : BinaryOperationType()
        data object Power: BinaryOperationType()
        data object Logarithm: BinaryOperationType()
    }

    sealed class LogicOperationType : OperationType() {
        data object NOT: LogicOperationType()
        data object OR: LogicOperationType()
        data object AND: LogicOperationType()
        data object XOR: LogicOperationType()
        data object NAND: LogicOperationType()
        data object NOR: LogicOperationType()
        data object SHIFT_R: LogicOperationType()
        data object SHIFT_L: LogicOperationType()
    }

}