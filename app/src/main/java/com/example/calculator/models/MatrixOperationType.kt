package com.example.calculator.models

sealed class MatrixOperationType {

    sealed class Unary {
        object Transpose : Unary()
        object Inverse : Unary()
        object Determinant : Unary()
    }

    sealed class Binary {
        object Add : Binary()          // A + B
        object Multiply : Binary()     // A * B
        object xCoef: Binary()         // x * A
    }

}