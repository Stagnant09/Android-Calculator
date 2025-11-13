package com.example.calculator.ui.screens.calculus.functionGraph

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.foundation.CustomViewModel
import com.example.calculator.utlis.ExpressionParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

class FunctionGraphViewModel : ViewModel(), CustomViewModel<FunctionGraphContract.State, FunctionGraphContract.Event, FunctionGraphContract.Effect> {

    private val _uiState = MutableStateFlow(
        FunctionGraphContract.State(
            textFieldsContent = listOf("y = 0.05x", "y = x + 2^(x-1)"),
            functions = listOf(ExpressionParser.parse("y = 0.05x")!!, ExpressionParser.parse("y = x + 2^(x-1)")!!),
            functionColors = listOf(Color.Blue, Color.Magenta),
            scale = 1f,
            offsetX = 0f,
            offsetY = 0f
        )
    )
    val uiState: StateFlow<FunctionGraphContract.State> = _uiState.asStateFlow()

    override fun setState(state: FunctionGraphContract.State) {
        _uiState.value = state
    }

    override fun setEvent(event: FunctionGraphContract.Event) {
        viewModelScope.launch {
            handleEvent(event)
        }
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
                        },
                        functions = if (expression != null) {
                            _uiState.value.functions.toMutableList().apply {
                                set(event.index, expression)
                            }
                        } else {
                            _uiState.value.functions
                        }
                    )
                )
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
                        textFieldsContent = _uiState.value.textFieldsContent + "y = 0",
                        functions = _uiState.value.functions + ExpressionParser.parse("y = 0")!!,
                        functionColors = _uiState.value.functionColors + Color.Gray
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
            
            is FunctionGraphContract.Event.ZoomIn -> {
                val newScale = _uiState.value.scale * event.factor
                setState(
                    _uiState.value.copy(
                        scale = newScale.coerceIn(0.1f, 10f)
                    )
                )
            }
            
            is FunctionGraphContract.Event.ZoomOut -> {
                val newScale = _uiState.value.scale * event.factor
                setState(
                    _uiState.value.copy(
                        scale = newScale.coerceIn(0.1f, 10f)
                    )
                )
            }
            
            is FunctionGraphContract.Event.Pan -> {
                setState(
                    _uiState.value.copy(
                        offsetX = (_uiState.value.offsetX + event.dx / _uiState.value.scale).coerceIn(-1000f, 1000f),
                        offsetY = (_uiState.value.offsetY + event.dy / _uiState.value.scale).coerceIn(-1000f, 1000f)
                    )
                )
            }
            
            is FunctionGraphContract.Event.ResetView -> {
                setState(
                    _uiState.value.copy(
                        scale = 1f,
                        offsetX = 0f,
                        offsetY = 0f
                    )
                )
            }
        }
    }

}