package com.example.calculator.ui.screens.main.matrix

import androidx.lifecycle.ViewModel
import com.example.calculator.foundation.CustomViewModel
import com.example.calculator.ui.screens.main.matrix.MatrixScreenContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MatrixScreenViewModel : CustomViewModel<MatrixScreenContract.State, MatrixScreenContract.Event>, ViewModel() {
    private var _uiState = MutableStateFlow(MatrixScreenContract.State())
    val uiState: StateFlow<MatrixScreenContract.State> = _uiState.asStateFlow()

    override fun setState(state: MatrixScreenContract.State) {
        _uiState.value = state
    }

    override fun setEvent(event: MatrixScreenContract.Event) {
        handleEvent(event)
    }

    override fun handleEvent(event: MatrixScreenContract.Event) {
        when (event) {
            MatrixScreenContract.Event.TappedClearButton -> TODO()
            MatrixScreenContract.Event.TappedEqualButton -> TODO()
            is MatrixScreenContract.Event.TappedNumberField -> TODO()
            is MatrixScreenContract.Event.TappedOperationButton -> TODO()
        }
    }

}