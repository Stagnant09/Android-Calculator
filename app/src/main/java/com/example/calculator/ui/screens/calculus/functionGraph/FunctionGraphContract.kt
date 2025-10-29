package com.example.calculator.ui.screens.calculus.functionGraph

import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState

sealed interface FunctionGraphContract {
    sealed interface Event : CustomEvent {

    }
    sealed interface Effect : CustomEffect {

    }
    data class State(
        val placeholder: String = ""
    ) : CustomState
}