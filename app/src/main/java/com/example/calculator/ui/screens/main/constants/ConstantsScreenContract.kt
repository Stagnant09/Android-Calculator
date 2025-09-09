package com.example.calculator.ui.screens.main.constants

import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState

sealed interface ConstantsScreenContract {

    sealed interface Event : CustomEvent  {
        data object TappedFloatingActionButton : Event
        data class TappedPositiveButton(val value: String) : Event
    }

    data class State(val value: String = "") : CustomState

    sealed interface Effect : CustomEffect {
        data object ShowDialog : Effect
    }

}