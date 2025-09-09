package com.example.calculator.ui.screens.main.constants

import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState
import com.example.calculator.models.Constant

sealed interface ConstantsScreenContract {

    sealed interface Event : CustomEvent  {
        data object TappedFloatingActionButton : Event
        data class TappedPositiveButton(val value: String) : Event
    }

    data class State(
        val value: String = "",
        val results: List<Constant> = emptyList()
    ) : CustomState

    sealed interface Effect : CustomEffect {
        data object ShowDialog : Effect
        data object ShowResults : Effect
    }

}