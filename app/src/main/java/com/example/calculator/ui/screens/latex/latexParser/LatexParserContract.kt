package com.example.calculator.ui.screens.latex.latexParser

import androidx.compose.ui.graphics.ImageBitmap
import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState

sealed interface LatexParserContract {
    sealed interface Event : CustomEvent {
        data class ParseLatex(val latex: String) : Event
        data class StoreImage(val image: ImageBitmap) : Event
        data class SaveAsImage(val latex: String) : Event
        data class SaveAsPdf(val latex: String) : Event
        data class Share(val latex: String) : Event
    }

    data class State(
        val latex: String,
        val result: ImageBitmap = ImageBitmap(0,0) //empty image bitmap
    ) : CustomState

    sealed interface Effect : CustomEffect {

    }
}