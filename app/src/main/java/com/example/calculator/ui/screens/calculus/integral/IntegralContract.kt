package com.example.calculator.ui.screens.calculus.integral

import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState

sealed interface IntegralContract {
    data class State(val expression: String = "") : CustomState
    sealed interface Event : CustomEvent
    sealed interface Effect : CustomEffect
}