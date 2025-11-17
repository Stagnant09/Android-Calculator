package com.example.calculator.ui.screens.latex.latexCharacterMap

import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState

sealed interface LatexCharacterMapContract {

    sealed interface Event : CustomEvent {

    }

    data class State(
        val any: Any
    ) : CustomState

    sealed interface Effect : CustomEffect {

    }

}