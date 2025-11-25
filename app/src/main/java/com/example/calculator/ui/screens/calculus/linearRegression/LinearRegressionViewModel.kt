package com.example.calculator.ui.screens.calculus.linearRegression

import com.example.calculator.foundation.CustomViewModel
import kotlin.math.pow
import kotlin.math.sqrt

class LinearRegressionViewModel :
    CustomViewModel<LinearRegressionContract.State, LinearRegressionContract.Event, LinearRegressionContract.Effect>(
        initialState = LinearRegressionContract.State()
    ) {

    override suspend fun handleEvent(event: LinearRegressionContract.Event) {
        when (event) {

            is LinearRegressionContract.Event.AddPoint -> {
                val newPoints = uiState.value.points + LinearRegressionContract.Point(event.x, event.y)
                setState { uiState.value.copy(points = newPoints) }
                computeRegression(newPoints)
            }

            is LinearRegressionContract.Event.SelectPoint -> {
                setState { uiState.value.copy(selectedPointIndex = event.index) }
            }

            is LinearRegressionContract.Event.MovePoint -> {
                val updated = uiState.value.points.toMutableList()
                updated[event.index] = LinearRegressionContract.Point(event.x, event.y)
                setState { uiState.value.copy(points = updated) }
                computeRegression(updated)
            }

            is LinearRegressionContract.Event.RemoveSelectedPoint -> {
                val index = uiState.value.selectedPointIndex ?: return
                val updated = uiState.value.points.toMutableList().apply { removeAt(index) }
                setState { uiState.value.copy(points = updated, selectedPointIndex = null) }
                computeRegression(updated)
            }
        }
    }

    private fun computeRegression(points: List<LinearRegressionContract.Point>) {
        if (points.size < 2) {
            setState { uiState.value.copy(regression = null) }
            return
        }

        val n = points.size
        val sumX = points.sumOf { it.x.toDouble() }
        val sumY = points.sumOf { it.y.toDouble() }
        val sumXY = points.sumOf { (it.x * it.y).toDouble() }
        val sumX2 = points.sumOf { (it.x * it.x).toDouble() }
        val sumY2 = points.sumOf { (it.y * it.y).toDouble() }

        val a = ((n * sumXY) - (sumX * sumY)) / (n * sumX2 - sumX * sumX)
        val b = (sumY - a * sumX) / n

        val covariance = (sumXY / n) - (sumX / n) * (sumY / n)
        val correlation = covariance / sqrt(
            (sumX2 / n - (sumX / n).pow(2)) *
                    (sumY2 / n - (sumY / n).pow(2))
        )

        setState {
            uiState.value.copy(
                regression = LinearRegressionContract.RegressionResult(
                    a = a.toFloat(),
                    b = b.toFloat(),
                    covariance = covariance.toFloat(),
                    correlation = correlation.toFloat()
                )
            )
        }
    }
}