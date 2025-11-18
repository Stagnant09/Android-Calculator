package com.example.calculator.ui.screens.latex.latexCharacterMap

import com.example.calculator.foundation.CustomViewModel

class LatexCharacterMapViewModel : CustomViewModel<LatexCharacterMapContract.State, LatexCharacterMapContract.Event, LatexCharacterMapContract.Effect>(
    initialState = LatexCharacterMapContract.State(
        latexCharacter = ""
    )
) {
    override suspend fun handleEvent(event: LatexCharacterMapContract.Event) {
        when (event) {
            is LatexCharacterMapContract.Event.SetLatexCharacter -> {
                setState(
                    uiState.value.copy(latexCharacter = event.latexCharacter)
                )
            }
        }
    }
}