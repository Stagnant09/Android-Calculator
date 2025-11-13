package com.example.calculator.ui.screens.calculus.integral

import com.example.calculator.foundation.CustomViewModel

class IntegralViewModel : CustomViewModel<IntegralContract.State, IntegralContract.Event, IntegralContract.Effect>(
    initialState = IntegralContract.State()
) {
    override suspend fun handleEvent(event: IntegralContract.Event) {

    }
}
