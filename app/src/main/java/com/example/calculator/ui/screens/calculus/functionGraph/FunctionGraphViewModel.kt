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

class FunctionGraphViewModel : CustomViewModel<FunctionGraphContract.State, FunctionGraphContract.Event, FunctionGraphContract.Effect>(
    initialState = FunctionGraphContract.State(
        textFieldsContent = listOf("y = 0.05x", "y = x + 2^(x-1)"),
        functions = listOf(ExpressionParser.parse("y = 0.05x")!!, ExpressionParser.parse("y = x + 2^(x-1)")!!),
        functionColors = listOf(Color.Blue, Color.Magenta),
        scale = 1f,
        offsetX = 0f,
        offsetY = 0f
    )
) {

    override suspend fun handleEvent(event: FunctionGraphContract.Event) {
        when (event) {
            is FunctionGraphContract.Event.UpdateTextField -> {
                val expression = try {
                    ExpressionParser.parse(event.input)?.normalize()
                } catch (e: Exception) {
                    null
                }
                setState(
                    uiState.value.copy(
                        textFieldsContent = uiState.value.textFieldsContent.toMutableList().apply {
                            set(event.index, event.input)
                        },
                        functions = if (expression != null) {
                            uiState.value.functions.toMutableList().apply {
                                set(event.index, expression)
                            }
                        } else {
                            uiState.value.functions
                        }
                    )
                )
            }
            
            FunctionGraphContract.Event.TappedSettingsButton -> {
                setState(
                    uiState.value.copy(
                        showBottomSheet = true
                    )
                )
            }
            
            FunctionGraphContract.Event.DismissBottomSheet -> {
                setState(
                    uiState.value.copy(
                        showBottomSheet = false
                    )
                )
            }
            
            FunctionGraphContract.Event.ToggledIntersectionPoints -> {
                setState(
                    uiState.value.copy(
                        showIntersectionPoints = !uiState.value.showIntersectionPoints
                    )
                )
            }
            
            FunctionGraphContract.Event.ToggledLabels -> {
                setState(
                    uiState.value.copy(
                        showLabels = !uiState.value.showLabels
                    )
                )
            }
            
            FunctionGraphContract.Event.AddFunction -> {
                setState(
                    uiState.value.copy(
                        textFieldsContent = uiState.value.textFieldsContent + "y = 0",
                        functions = uiState.value.functions + ExpressionParser.parse("y = 0")!!,
                        functionColors = uiState.value.functionColors + Color.Gray
                    )
                )
            }
            
            is FunctionGraphContract.Event.RemoveFunction -> {
                setState(
                    uiState.value.copy(
                        textFieldsContent = uiState.value.textFieldsContent.toMutableList().apply {
                            removeAt(event.index)
                        },
                        functions = uiState.value.functions.toMutableList().apply {
                            removeAt(event.index)
                        },
                        functionColors = uiState.value.functionColors.toMutableList().apply {
                            removeAt(event.index)
                        }
                    )
                )
            }
            
            is FunctionGraphContract.Event.SetFunctionColor -> {
                setState(
                    uiState.value.copy(
                        functionColors = uiState.value.functionColors.toMutableList().apply {
                            set(event.index, event.color)
                        }
                    )
                )
            }
            
            FunctionGraphContract.Event.ToggledColorPicker -> {
                setState(
                    uiState.value.copy(
                        showColorPicker = !uiState.value.showColorPicker
                    )
                )
            }
            
            is FunctionGraphContract.Event.SetCurrentIndex -> {
                setState(
                    uiState.value.copy(
                        currentIndex = event.index
                    )
                )
            }
            
            is FunctionGraphContract.Event.ZoomIn -> {
                val newScale = uiState.value.scale * event.factor
                setState(
                    uiState.value.copy(
                        scale = newScale.coerceIn(0.1f, 10f)
                    )
                )
            }
            
            is FunctionGraphContract.Event.ZoomOut -> {
                val newScale = uiState.value.scale * event.factor
                setState(
                    uiState.value.copy(
                        scale = newScale.coerceIn(0.1f, 10f)
                    )
                )
            }
            
            is FunctionGraphContract.Event.Pan -> {
                setState(
                    uiState.value.copy(
                        offsetX = (uiState.value.offsetX + event.dx / uiState.value.scale).coerceIn(-1000f, 1000f),
                        offsetY = (uiState.value.offsetY + event.dy / uiState.value.scale).coerceIn(-1000f, 1000f)
                    )
                )
            }
            
            is FunctionGraphContract.Event.ResetView -> {
                setState(
                    uiState.value.copy(
                        scale = 1f,
                        offsetX = 0f,
                        offsetY = 0f
                    )
                )
            }
        }
    }

}