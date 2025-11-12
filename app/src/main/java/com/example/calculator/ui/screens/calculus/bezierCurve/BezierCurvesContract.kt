package com.example.calculator.ui.screens.calculus.bezierCurve

import androidx.compose.ui.graphics.Color
import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState

sealed interface BezierCurvesContract {
    sealed interface Event : CustomEvent {
        data object ResetView : Event
        data class Pan(val dx: Float, val dy: Float) : Event
        data class ZoomIn(val scale: Float) : Event
        data class ZoomOut(val scale: Float) : Event
        data class MoveStart(val dx: Float, val dy: Float) : Event
        data class MoveEnd(val dx: Float, val dy: Float) : Event
        data class MoveControlPoint(val index: Int, val dx: Float, val dy: Float) : Event
        data class TextFieldEdit(val index: Int, val value: String) : Event
        data object ToggledColorPicker : Event
        data class SetPointColor(val index: Int, val color: Color) : Event
        data class SetCurrentIndex(val index: Int) : Event
    }

    data class State(
        val start: Pair<Float, Float> = Pair(-5f, 0f),
        val end: Pair<Float, Float> = Pair(5f, 2f),
        val controlPoints: List<Pair<Float, Float>> = listOf(Pair(0f, -1f), Pair(1f, -1f)),
        val colors: List<Color> = listOf(Color.Green, Color.Green, Color.Red, Color.Red),
        val textFieldValues: List<String> = listOf("(-5, 0)", "(5, 2)", "(0, -1)", "(1, -1)"),
        val scale: Float = 1f,
        val offsetX: Float = 0f,
        val offsetY: Float = 0f,
        val showColorPicker: Boolean = false,
        val currentIndex: Int = 0
    ) : CustomState

    sealed interface Effect : CustomEffect {}
}