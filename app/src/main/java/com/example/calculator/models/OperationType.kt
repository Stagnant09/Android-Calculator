package com.example.calculator.models

sealed class OperationType {

    sealed class UnaryOperationType : OperationType() {
        data object Ceil : UnaryOperationType()
        data object Floor : UnaryOperationType()
        data object AbsoluteValue : UnaryOperationType()
        data object Sqrt: UnaryOperationType()
        data object Square: UnaryOperationType()
        data object Cube: UnaryOperationType()
        data object Factorial: UnaryOperationType()
        data object Reciprocal: UnaryOperationType()
        data object Ln: UnaryOperationType()
        data object Log: UnaryOperationType()
        data object Sin: UnaryOperationType()
        data object Cos: UnaryOperationType()
        data object Tan: UnaryOperationType()
        data object Cot: UnaryOperationType()
        data object Asin: UnaryOperationType()
        data object Acos: UnaryOperationType()
        data object Atan: UnaryOperationType()
        data object Acot: UnaryOperationType()
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

    sealed class Constant: OperationType() {
        data object Pi: Constant()
        data object E: Constant()
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

    data object Alternative: OperationType()
    data object Mode: OperationType()
    data object OpenParenthesis: OperationType()
    data object CloseParenthesis: OperationType()
}