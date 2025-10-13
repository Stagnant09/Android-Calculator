package com.example.calculator.ui.screens.main.combinatorics

import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState
import com.example.calculator.models.CombinatoricsOperationType


sealed interface CombinatoricsScreenContract {

    sealed interface Event : CustomEvent {
        data class UpdateN(val n: Int) : Event
        data class UpdateK(val k: Int) : Event
    }

    data class State(
        val n: Int = 1,
        val k: Int = 1,
        val permutationsWithoutRepetition: Int = 1,
        val permutationsWithRepetition: Int = 1,
        val combinationsWithoutRepetition: Int = 1,
        val combinationsWithRepetition: Int = 1
    ) : CustomState

    sealed interface Effect : CustomEffect {

    }

}