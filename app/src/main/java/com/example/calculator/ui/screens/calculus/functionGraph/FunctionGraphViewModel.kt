package com.example.calculator.ui.screens.calculus.functionGraph

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.calculator.foundation.CustomViewModel
import com.example.calculator.ui.screens.calculus.functionGraph.FunctionGraphContract
import com.example.calculator.utlis.ExpressionParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FunctionGraphViewModel : CustomViewModel<FunctionGraphContract.State, FunctionGraphContract.Event, FunctionGraphContract.Effect>, ViewModel() {

    private var _uiState = MutableStateFlow(FunctionGraphContract.State(
        textFieldsContent = listOf("y = 0.05x", "y = x + 2^(x-1)"),
        functions = listOf(ExpressionParser.parse("y = 0.05x")!!, ExpressionParser.parse("y = x + 2^(x-1)")!!),
        functionColors = listOf(Color.Blue, Color.Magenta)
    ))
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
                val expression = try {
                    ExpressionParser.parse(event.input)?.normalize()
                } catch (e: Exception) {
                    null
                }
                setState(
                    _uiState.value.copy(
                        textFieldsContent = _uiState.value.textFieldsContent.toMutableList().apply {
                            set(event.index, event.input)
                        }
                    )
                )
                if (expression != null) {
                    setState(
                        _uiState.value.copy(
                            functions = _uiState.value.functions.toMutableList().apply {
                                set(event.index, expression)
                            }
                        )
                    )
                }
            }
            FunctionGraphContract.Event.TappedSettingsButton -> {
                setState(
                    _uiState.value.copy(
                        showBottomSheet = true
                    )
                )
            }
            FunctionGraphContract.Event.DismissBottomSheet -> {
                setState(
                    _uiState.value.copy(
                        showBottomSheet = false
                    )
                )
            }

            FunctionGraphContract.Event.ToggledIntersectionPoints -> {
                setState(
                    _uiState.value.copy(
                        showIntersectionPoints = !_uiState.value.showIntersectionPoints
                    )
                )
            }

            FunctionGraphContract.Event.ToggledLabels -> {
                setState(
                    _uiState.value.copy(
                        showLabels = !_uiState.value.showLabels
                    )
                )
            }

            FunctionGraphContract.Event.AddFunction -> {
                setState(
                    _uiState.value.copy(
                        textFieldsContent = _uiState.value.textFieldsContent.toMutableList().apply {
                            add("y = 0")
                        },
                        functions = _uiState.value.functions.toMutableList().apply {
                            add(ExpressionParser.parse("y = 0")!!)
                        },
                        functionColors = _uiState.value.functionColors.toMutableList().apply {
                            add(Color.DarkGray)
                        }
                    )
                )
            }

            is FunctionGraphContract.Event.RemoveFunction -> {
                setState(
                    _uiState.value.copy(
                        textFieldsContent = _uiState.value.textFieldsContent.toMutableList().apply {
                            removeAt(event.index)
                        },
                        functions = _uiState.value.functions.toMutableList().apply {
                            removeAt(event.index)
                        },
                        functionColors = _uiState.value.functionColors.toMutableList().apply {
                            removeAt(event.index)
                        }
                    )
                )
            }

            is FunctionGraphContract.Event.SetFunctionColor -> {
                setState(
                    _uiState.value.copy(
                        functionColors = _uiState.value.functionColors.toMutableList().apply {
                            set(event.index, event.color)
                        }
                    )
                )
            }
            FunctionGraphContract.Event.ToggledColorPicker -> {
                setState(
                    _uiState.value.copy(
                        showColorPicker = !_uiState.value.showColorPicker
                    )
                )
            }

            is FunctionGraphContract.Event.SetCurrentIndex -> {
                setState(
                    _uiState.value.copy(
                        currentIndex = event.index
                    )
                )
            }
        }
    }

}