package com.example.calculator.ui.screens.latex.latexParser

import com.example.calculator.foundation.CustomViewModel

class LatexParserViewModel :
    CustomViewModel<LatexParserContract.State, LatexParserContract.Event, LatexParserContract.Effect>(
        initialState = LatexParserContract.State(
            latex = "\\dfrac{1}{2} \\int_0^1{x^2dx} = \\dfrac{1}{6}",
            result = ""
        )
    ) {

    override suspend fun handleEvent(event: LatexParserContract.Event) {
        when (event) {
            is LatexParserContract.Event.ParseLatex -> {
                setState(
                    uiState.value.copy(
                        latex = event.latex,
                        result = ""
                    )
                )
            }

            is LatexParserContract.Event.SaveAsImage -> {

            }
            is LatexParserContract.Event.SaveAsPdf -> {

            }
            is LatexParserContract.Event.Share -> {

            }
        }
    }

}