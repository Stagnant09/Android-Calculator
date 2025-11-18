package com.example.calculator.ui.screens.latex.latexParser

import com.example.calculator.foundation.CustomViewModel
import com.example.calculator.interactors.LatexInteractor
import com.example.calculator.models.ExportRequestType

class LatexParserViewModel(
    private val interactor: LatexInteractor
) :
    CustomViewModel<LatexParserContract.State, LatexParserContract.Event, LatexParserContract.Effect>(
        initialState = LatexParserContract.State(
            latex = "f(\\vec{x}) = \\dfrac{1}{\\sqrt{(2\\pi)^k \\Sigma}} \\cdot \\exp{(-\\dfrac{1}{2}(\\vec{x} - \\mu)^T\\Sigma^{-1}(\\vec{x} - \\mu))}",
        )
    ) {

    override suspend fun handleEvent(event: LatexParserContract.Event) {
        when (event) {
            is LatexParserContract.Event.ParseLatex -> {
                setState(
                    uiState.value.copy(
                        latex = event.latex
                    )
                )
            }
            is LatexParserContract.Event.StoreImage -> {
                setState(
                    uiState.value.copy(
                        result = event.image
                    )
                )
            }

            is LatexParserContract.Event.SaveAsImage -> {
                interactor.exportLatex(ExportRequestType.SAVE_AS_IMAGE, uiState.value.result, event.context)
            }
            is LatexParserContract.Event.SaveAsPdf -> {
                interactor.exportLatex(ExportRequestType.SAVE_AS_PDF, uiState.value.result, event.context)
            }
            is LatexParserContract.Event.Share -> {
                interactor.exportLatex(ExportRequestType.SHARE, uiState.value.result, event.context)
            }
        }
    }

}