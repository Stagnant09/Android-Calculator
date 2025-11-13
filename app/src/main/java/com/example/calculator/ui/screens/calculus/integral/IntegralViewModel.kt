package com.example.calculator.ui.screens.calculus.integral

import com.example.calculator.foundation.CustomViewModel

class IntegralViewModel : CustomViewModel<IntegralContract.State, IntegralContract.Event, IntegralContract.Effect>(
    initialState = IntegralContract.State(
        lowerIntegralValue = "0",
        upperIntegralValue = "1",
        expression = "x^2"
    )
) {
    override suspend fun handleEvent(event: IntegralContract.Event) {
        when (event) {
            is IntegralContract.Event.UpdateLowerIntegralValue -> {
                setState {
                    uiState.value.copy(
                        lowerIntegralValue = event.value
                    )
                }
            }
            is IntegralContract.Event.UpdateUpperIntegralValue -> {
                setState {
                    uiState.value.copy(
                        upperIntegralValue = event.value
                    )
                }
            }
            is IntegralContract.Event.UpdateIntegralExpression -> {
                setState {
                    uiState.value.copy(
                        expression = event.expression
                    )
                }
            }
        }
    }
}
