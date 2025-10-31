package com.example.calculator.ui.screens.calculus.functionGraph

import androidx.lifecycle.ViewModel
import com.example.calculator.foundation.CustomViewModel
import com.example.calculator.ui.screens.calculus.functionGraph.FunctionGraphContract
import com.example.calculator.utlis.ExpressionParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FunctionGraphViewModel : CustomViewModel<FunctionGraphContract.State, FunctionGraphContract.Event, FunctionGraphContract.Effect>, ViewModel() {

    private var _uiState = MutableStateFlow(FunctionGraphContract.State())
    val uiState: StateFlow<FunctionGraphContract.State> = _uiState.asStateFlow()

    override fun setState(state: FunctionGraphContract.State) {
        _uiState.value = state
    }

    override fun setEvent(event: FunctionGraphContract.Event) {
        handleEvent(event)
    }

    override fun handleEvent(event: FunctionGraphContract.Event) {
        when (event) {
            is FunctionGraphContract.Event.UpdateTextField -> {
                setState(
                    _uiState.value.copy(
                        textFieldsContent = _uiState.value.textFieldsContent.toMutableList().apply {
                            set(event.index, event.input)
                        },
                        functions = _uiState.value.functions.toMutableList().apply {
                            set(event.index, ExpressionParser.parse(event.input)).normalize()
                        }
                    )
                )
            }
            else -> {

            }
        }
    }

}