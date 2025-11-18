package com.example.calculator.ui.screens.calculus.bezierCurve

import androidx.compose.ui.graphics.Color
import com.example.calculator.foundation.CustomViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BezierCurvesViewModel :
    CustomViewModel<BezierCurvesContract.State, BezierCurvesContract.Event, BezierCurvesContract.Effect>(
        initialState = BezierCurvesContract.State()
    ) {

    override suspend fun handleEvent(event: BezierCurvesContract.Event) {
        when (event) {
            is BezierCurvesContract.Event.ResetView -> resetView()
            is BezierCurvesContract.Event.Pan -> pan(event.dx, event.dy)
            is BezierCurvesContract.Event.ZoomIn -> zoomIn(event.scale)
            is BezierCurvesContract.Event.ZoomOut -> zoomOut(event.scale)
            is BezierCurvesContract.Event.MoveStart -> moveStart(event.dx, event.dy)
            is BezierCurvesContract.Event.MoveEnd -> moveEnd(event.dx, event.dy)
            is BezierCurvesContract.Event.MoveControlPoint -> moveControlPoint(event.index, event.dx, event.dy)
            is BezierCurvesContract.Event.TextFieldEdit -> textFieldEdit(event.index, event.value)
            is BezierCurvesContract.Event.ToggledColorPicker -> toggledColorPicker()
            is BezierCurvesContract.Event.SetPointColor -> setPointColor(event.index, event.color)
            is BezierCurvesContract.Event.SetCurrentIndex -> setCurrentIndex(event.index)
        }
    }

    private fun resetView() {

    }

    private fun pan(dx: Float, dy: Float) {

    }

    private fun zoomIn(scale: Float) {

    }

    private fun zoomOut(scale: Float) {

    }

    private fun moveStart(dx: Float, dy: Float) {
        setState(
            uiState.value.copy(
                start = Pair(
                    uiState.value.start.first + dx,
                    uiState.value.start.second + dy
                )
            )
        )
    }

    private fun moveEnd(dx: Float, dy: Float) {
        setState(
            uiState.value.copy(
                end = Pair(
                    uiState.value.end.first + dx,
                    uiState.value.end.second + dy
                )
            )
        )
    }

    private fun moveControlPoint(index: Int, dx: Float, dy: Float) {
        val controlPoint = uiState.value.controlPoints[index]
        setState(
            uiState.value.copy(
                controlPoints = uiState.value.controlPoints.toMutableList().apply {
                    this[index] = controlPoint.copy(
                        controlPoint.first + dx,
                        controlPoint.second + dy
                    )
                }
            )
        )
    }

    /**
     * Attempts to parse a coordinate string (e.g., "(5, 2)" or "5, 2") into a Pair<Float, Float>.
     * Returns null if parsing fails.
     */
    private fun parseCoordinate(input: String): Pair<Float, Float>? {
        // Clean the string (remove parentheses, spaces)
        val cleanedInput = input.trim().removePrefix("(").removeSuffix(")").replace(" ", "")

        // Split by comma
        val parts = cleanedInput.split(",")

        if (parts.size == 2) {
            val x = parts[0].toFloatOrNull()
            val y = parts[1].toFloatOrNull()

            // Check if both parts are valid floats
            if (x != null && y != null) {
                return Pair(x, y)
            }
        }
        return null
    }

    private fun textFieldEdit(index: Int, value: String) {
        // 1. Update the text field value immediately
        val newTextFieldValues = uiState.value.textFieldValues.toMutableList().apply {
            this[index] = value
        }

        // 2. Try to parse the new value into coordinates
        val newCoordinate = parseCoordinate(value)

        // 3. Update the state based on the index and whether parsing was successful
        val newState = when (index) {
            0 -> { // Start Point
                uiState.value.copy(
                    start = newCoordinate ?: uiState.value.start, // Use new coord if valid, otherwise keep old coord
                    textFieldValues = newTextFieldValues
                )
            }
            1 -> { // End Point
                uiState.value.copy(
                    end = newCoordinate ?: uiState.value.end, // Use new coord if valid, otherwise keep old coord
                    textFieldValues = newTextFieldValues
                )
            }
            else -> { // Control Points (index >= 2)
                val controlPointIndex = index - 2
                val newControlPoints = uiState.value.controlPoints.toMutableList()

                if (newCoordinate != null && controlPointIndex < newControlPoints.size) {
                    newControlPoints[controlPointIndex] = newCoordinate
                }

                uiState.value.copy(
                    controlPoints = newControlPoints,
                    textFieldValues = newTextFieldValues
                )
            }
        }

        setState(newState)
    }

    private fun toggledColorPicker() {
        setState(
            uiState.value.copy(
                showColorPicker = !uiState.value.showColorPicker
            )
        )
    }

    private fun setPointColor(index: Int, color: Color) {
        setState(
            uiState.value.copy(
                colors = uiState.value.colors.toMutableList().apply {
                    this[index] = color
                }
            )
        )
    }

    private fun setCurrentIndex(index: Int) {
        setState(
            uiState.value.copy(
                currentIndex = index
            )
        )
    }

}