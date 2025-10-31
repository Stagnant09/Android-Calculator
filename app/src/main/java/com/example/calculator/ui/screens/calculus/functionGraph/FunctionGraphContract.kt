package com.example.calculator.ui.screens.calculus.functionGraph

import androidx.compose.ui.graphics.Color
import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState
import com.example.calculator.utlis.Expression
import com.example.calculator.utlis.ExpressionParser

sealed interface FunctionGraphContract {
    sealed interface Event : CustomEvent {
        data class UpdateTextField(val index: Int, val input: String) : Event
    }

    sealed interface Effect : CustomEffect {

    }

    data class State(
        val textFieldsContent: List<String> = listOf("y = 2x + 4", "y = x"),
        val functions: List<Expression> = listOf(ExpressionParser.parse("y = sinx"), ExpressionParser.parse("y = x")),
        val functionColors: List<Color> = listOf(Color.Blue, Color.Magenta)
    ) : CustomState
}