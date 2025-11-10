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
        data object AddFunction : Event
        data class RemoveFunction(val index: Int) : Event

        data class SetFunctionColor(val index: Int, val color: Color) : Event
        data object ToggledColorPicker : Event
        data class SetCurrentIndex(val index: Int) : Event
        data class ZoomIn(val factor: Float = 1.2f) : Event
        data class ZoomOut(val factor: Float = 0.8f) : Event
        data class Pan(val dx: Float, val dy: Float) : Event
        data object ResetView : Event
    }

    sealed interface Effect : CustomEffect {

    }

    data class State(
        val textFieldsContent: List<String> = emptyList<String>(),
        val functions: List<Expression> = emptyList<Expression>(),
        val functionColors: List<Color> = emptyList<Color>(),
        val showBottomSheet: Boolean = false,
        val showIntersectionPoints: Boolean = false,
        val showLabels: Boolean = false,
        val showColorPicker: Boolean = false,
        val currentIndex: Int = -1,
        val scale: Float = 1f,
        val offsetX: Float = 0f,
        val offsetY: Float = 0f
    ) : CustomState
}