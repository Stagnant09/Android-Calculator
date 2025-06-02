package com.example.calculator.ui.screens.main.components

import com.example.calculator.models.OperationType
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState

sealed interface MainScreenContract {

    sealed class Event : CustomEvent {
        data class TappedOperationButton(val operationType: OperationType) : Event()
        data class TappedNumberButton(val value: Float) : Event()
        data object TappedEqualButton : Event()
        data object TappedClearButton : Event()
        data object TappedDecimalButton : Event()
    }

    data class State(
        val value1: Float = 0F,
        val value2: Float = 0F,
        val firstOperation: Boolean = true,
        val currentOperation: OperationType? = null,
        val powerOfTen: Int = 0
    ) : CustomState
}