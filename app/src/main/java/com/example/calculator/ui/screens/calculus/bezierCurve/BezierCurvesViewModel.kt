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

}