package com.example.calculator.ui.screens.main.currency

import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState

sealed interface CurrencyScreenContract {

    sealed interface Event : CustomEvent {
        data object Init : Event
        data object Refresh : Event
        data class OnInputChanged(val index: Int, val input: String) : Event
    }

    data class State(
        val currencyExchangeValues: List<Float> = List(31) { 0f },
        val currencyDisplayValues: List<Float> = List(31) { 0f }
    ) : CustomState

    sealed interface Effect : CustomEffect {

    }

}