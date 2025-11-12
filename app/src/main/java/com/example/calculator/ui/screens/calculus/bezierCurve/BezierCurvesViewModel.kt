package com.example.calculator.ui.screens.calculus.bezierCurve

import com.example.calculator.foundation.CustomViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BezierCurvesViewModel :
    CustomViewModel<BezierCurvesContract.State, BezierCurvesContract.Event, BezierCurvesContract.Effect> {
    private val _uiState = MutableStateFlow(
        BezierCurvesContract.State()
    )
    val uiState = _uiState.asStateFlow()

    override fun setState(state: BezierCurvesContract.State) {
        _uiState.value = state
    }

    override fun setEvent(event: BezierCurvesContract.Event) {
        handleEvent(event)
    }

    override fun handleEvent(event: BezierCurvesContract.Event) {
        when (event) {
            is BezierCurvesContract.Event.ResetView -> resetView()
            is BezierCurvesContract.Event.Pan -> pan(event.dx, event.dy)
            is BezierCurvesContract.Event.ZoomIn -> zoomIn(event.scale)
            is BezierCurvesContract.Event.ZoomOut -> zoomOut(event.scale)
            is BezierCurvesContract.Event.MoveStart -> moveStart(event.dx, event.dy)
            is BezierCurvesContract.Event.MoveEnd -> moveEnd(event.dx, event.dy)
            is BezierCurvesContract.Event.MoveControlPoint -> moveControlPoint(event.index, event.dx, event.dy)
            is BezierCurvesContract.Event.TextFieldEdit -> textFieldEdit(event.index, event.value)
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
            _uiState.value.copy(
                start = Pair(
                    _uiState.value.start.first + dx,
                    _uiState.value.start.second + dy
                )
            )
        )
    }

    private fun moveEnd(dx: Float, dy: Float) {
        setState(
            _uiState.value.copy(
                end = Pair(
                    _uiState.value.end.first + dx,
                    _uiState.value.end.second + dy
                )
            )
        )
    }

    private fun moveControlPoint(index: Int, dx: Float, dy: Float) {
        val controlPoint = _uiState.value.controlPoints[index]
        setState(
            _uiState.value.copy(
                controlPoints = _uiState.value.controlPoints.toMutableList().apply {
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
        val newTextFieldValues = _uiState.value.textFieldValues.toMutableList().apply {
            this[index] = value
        }

        // 2. Try to parse the new value into coordinates
        val newCoordinate = parseCoordinate(value)

        // 3. Update the state based on the index and whether parsing was successful
        val newState = when (index) {
            0 -> { // Start Point
                _uiState.value.copy(
                    start = newCoordinate ?: _uiState.value.start, // Use new coord if valid, otherwise keep old coord
                    textFieldValues = newTextFieldValues
                )
            }
            1 -> { // End Point
                _uiState.value.copy(
                    end = newCoordinate ?: _uiState.value.end, // Use new coord if valid, otherwise keep old coord
                    textFieldValues = newTextFieldValues
                )
            }
            else -> { // Control Points (index >= 2)
                val controlPointIndex = index - 2
                val newControlPoints = _uiState.value.controlPoints.toMutableList()

                if (newCoordinate != null && controlPointIndex < newControlPoints.size) {
                    newControlPoints[controlPointIndex] = newCoordinate
                }

                _uiState.value.copy(
                    controlPoints = newControlPoints,
                    textFieldValues = newTextFieldValues
                )
            }
        }

        setState(newState)
    }

}