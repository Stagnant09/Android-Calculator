package com.example.calculator.ui.screens.calculus.integral

import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState

sealed interface IntegralContract {
    data class State(
        val lowerIntegralValue: String = "",
        val upperIntegralValue: String = "",
        val expression: String = ""
    ) : CustomState
    sealed interface Event : CustomEvent {
        data class UpdateLowerIntegralValue(val value: String) : Event
        data class UpdateUpperIntegralValue(val value: String) : Event
        data class UpdateIntegralExpression(val expression: String) : Event
    }
    sealed interface Effect : CustomEffect
}