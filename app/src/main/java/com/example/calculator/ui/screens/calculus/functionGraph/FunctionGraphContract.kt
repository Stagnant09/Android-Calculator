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
        data object TappedSettingsButton : Event
        data object DismissBottomSheet : Event
        data object ToggledIntersectionPoints : Event
        data object ToggledLabels : Event
    }

    sealed interface Effect : CustomEffect {

    }

    data class State(
        val textFieldsContent: List<String> = emptyList<String>(),
        val functions: List<Expression> = emptyList<Expression>(),
        val functionColors: List<Color> = emptyList<Color>(),
        val showBottomSheet: Boolean = false,
        val showIntersectionPoints: Boolean = false,
        val showLabels: Boolean = false
    ) : CustomState
}