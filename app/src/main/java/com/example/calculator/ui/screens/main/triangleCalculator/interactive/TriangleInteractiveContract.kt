package com.example.calculator.ui.screens.main.triangleCalculator.interactive

import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState
import com.example.calculator.models.Vertices

sealed interface TriangleInteractiveContract {

    sealed interface Event : CustomEvent {
        data class OnInputChanged(val coordinates: Vertices): Event // When a user drags a vertex
    }

    data class State(
        val angleA: Double = 0.0,
        val angleB: Double = 0.0,
        val angleC: Double = 0.0,
        val sinA: Double = 0.0,
        val sinB: Double = 0.0,
        val sinC: Double = 0.0,
        val cosA: Double = 0.0,
        val cosB: Double = 0.0,
        val cosC: Double = 0.0,
        val tanA: Double = 0.0,
        val tanB: Double = 0.0,
        val tanC: Double = 0.0,
        val sideA: Double = 0.0,
        val sideB: Double = 0.0,
        val sideC: Double = 0.0,
        val perimeter: Double = 0.0,
        val area: Double = 0.0,
        val centroid: Pair<Double, Double> = Pair(0.0, 0.0),
        val incenter: Pair<Double, Double> = Pair(0.0, 0.0),
        val circumcenter: Pair<Double, Double> = Pair(0.0, 0.0),
        val orthocenter: Pair<Double, Double> = Pair(0.0, 0.0),
    ) : CustomState

    sealed interface Effect : CustomEffect {

    }

}