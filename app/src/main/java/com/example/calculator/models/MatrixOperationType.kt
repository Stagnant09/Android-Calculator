package com.example.calculator.models

sealed interface MatrixOperationType {

    sealed class Unary : MatrixOperationType {
        data object Transpose : Unary()
        data object Inverse : Unary()
        data object Determinant : Unary()
    }

    sealed class Binary : MatrixOperationType {
        data object AddMultiply : Binary()
    }

}

fun allOperations(): List<MatrixOperationType> = listOf(
    MatrixOperationType.Unary.Inverse,
    MatrixOperationType.Unary.Determinant,
    MatrixOperationType.Unary.Transpose,
    MatrixOperationType.Binary.AddMultiply
)

fun toLabel(operationType: MatrixOperationType): String = when (operationType) {
    MatrixOperationType.Unary.Determinant -> "Determinant"
    MatrixOperationType.Unary.Inverse -> "Inverse"
    MatrixOperationType.Unary.Transpose -> "Transpose"
    MatrixOperationType.Binary.AddMultiply -> "Add/Multiply"
}