package com.example.calculator.ui.screens.main.matrix

import com.example.calculator.foundation.CustomEffect
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
        data class TappedCoefField(val index: Short, val coef: Float) : Event()
        data class TappedEqualButton(val index: Short) : Event()
        data object TappedEqualButtonAdd: Event()
        data object TappedEqualButtonMultiply: Event()
        data object TappedClearButton : Event()
        data class OpenMatrixDimensionsModal(val matrix: Short, val rows: Int, val columns: Int) : Event()
        data class SetMatrixDimensions(val matrix: Short, val rows: Int, val columns: Int) : Event()
    }

    data class State(
        val currentOperation: MatrixOperationType,
        val matrixA: Matrix = Matrix(3, 3),
        val matrixB: Matrix = Matrix(3, 3),
        val matrixC: Matrix = Matrix(3, 3),
        val coefA: Float = 1f,
        val coefB: Float = 1f,
        val impVal: Float = 0f // important value such as a calculated determinant etc.
    ) : CustomState

    sealed class Effect : CustomEffect {
        data class ShowDimensionsModal(val matrix: Short) : Effect()
        data class ShowResultModal(val operationType: MatrixOperationType, val matrix: Matrix = Matrix(0, 0), val value: Float = 0f) : Effect()
    }
}