package com.example.calculator.ui.screens.main.combinatorics

import androidx.lifecycle.ViewModel
import com.example.calculator.foundation.CustomViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.pow

class CombinatoricsViewModel : CustomViewModel<CombinatoricsScreenContract.State, CombinatoricsScreenContract.Event, CombinatoricsScreenContract.Effect>(
    initialState = CombinatoricsScreenContract.State()
) {

    override suspend fun handleEvent(event: CombinatoricsScreenContract.Event) {
        when (event) {
            is CombinatoricsScreenContract.Event.UpdateN -> {
                setState(
                    uiState.value.copy(
                        n = event.n
                    )
                )
            }
            is CombinatoricsScreenContract.Event.UpdateK -> {
                setState(
                    uiState.value.copy(
                        k = event.k
                    )
                )
            }
        }
        evaluate()
    }

    /** Function that updates the derived quantities displayed in View */
    private fun evaluate() {
        val n = uiState.value.n
        val k = uiState.value.k

        // Factorial helper
        fun factorial(x: Int): Long =
            if (x <= 1) 1L else (2..x).fold(1L) { acc, i -> acc * i }

        // Handle invalid cases (like n < k)
        if (n < 0 || k < 0 || k > n) {
            setState(
                uiState.value.copy(
                    permutationsWithoutRepetition = Double.NaN.toInt(),
                    permutationsWithRepetition = Double.NaN.toInt(),
                    combinationsWithoutRepetition = Double.NaN.toInt(),
                    combinationsWithRepetition = Double.NaN.toInt()
                )
            )
            return
        }

        val permutationsWithoutRepetition =
            factorial(n) / factorial(n - k)

        val permutationsWithRepetition: Double =
            n.toDouble().pow(k.toDouble())

        val combinationsWithoutRepetition =
            factorial(n) / (factorial(k) * factorial(n - k))

        val combinationsWithRepetition =
            factorial(n + k - 1) / (factorial(k) * factorial(n - 1))

        setState(
            uiState.value.copy(
                permutationsWithoutRepetition = permutationsWithoutRepetition.toDouble().toInt(),
                permutationsWithRepetition = permutationsWithRepetition.toInt(),
                combinationsWithoutRepetition = combinationsWithoutRepetition.toDouble().toInt(),
                combinationsWithRepetition = combinationsWithRepetition.toDouble().toInt()
            )
        )
    }

}