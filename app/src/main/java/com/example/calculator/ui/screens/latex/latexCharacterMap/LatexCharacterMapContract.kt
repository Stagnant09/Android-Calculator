package com.example.calculator.ui.screens.latex.latexCharacterMap

import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState

sealed interface LatexCharacterMapContract {

    sealed interface Event : CustomEvent {
        data class SetLatexCharacter(val latexCharacter: String) : Event
    }

    data class State(
        val latexCharacter: String
    ) : CustomState

    sealed interface Effect : CustomEffect {

    }

}