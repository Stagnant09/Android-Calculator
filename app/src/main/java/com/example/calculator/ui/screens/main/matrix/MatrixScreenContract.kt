package com.example.calculator.ui.screens.main.matrix

import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState
import com.example.calculator.models.Matrix
import com.example.calculator.models.MatrixOperationType
import com.example.calculator.models.OperationType

sealed interface MatrixScreenContract {

    sealed class Event : CustomEvent {
        data class TappedTopOperationButton(val operationType: MatrixOperationType) : Event()
        data class TappedOperationButton(val operationType: OperationType) : Event()
        data class TappedNumberField(val matrix: Short, val row: Int, val column: Int, val value: Float) : Event()
        data object TappedEqualButton : Event()
        data object TappedClearButton : Event()
    }

    data class State(
        val currentOperation: MatrixOperationType,
        val matrix1: Matrix = Matrix(3, 3),
        val matrix2: Matrix = Matrix(3, 3),
        val matrix3: Matrix = Matrix(3, 3),
        val coef1: Float = 1f,
        val coef2: Float = 1f,
    ) : CustomState
}