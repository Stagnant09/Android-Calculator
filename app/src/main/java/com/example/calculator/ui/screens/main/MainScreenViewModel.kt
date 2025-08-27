package com.example.calculator.ui.screens.main

import androidx.lifecycle.ViewModel
import com.example.calculator.models.OperationType
import com.example.calculator.ui.screens.main.components.MainScreenContract
import com.example.calculator.foundation.CustomViewModel
import com.example.calculator.models.NumeralSystem
import com.example.calculator.utlis.convertFromRadians
import com.example.calculator.utlis.convertToRadians
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.tan

class MainScreenViewmodel : CustomViewModel<MainScreenContract.State, MainScreenContract.Event>, ViewModel() {

    private var _uiState = MutableStateFlow(MainScreenContract.State())
    val uiState: StateFlow<MainScreenContract.State> = _uiState.asStateFlow()

    override fun setState(state: MainScreenContract.State) {
        _uiState.value = state
    }

    override fun setEvent(event: MainScreenContract.Event) {
        handleEvent(event)
    }

    override fun handleEvent(event: MainScreenContract.Event) {
        when (event) {
            is MainScreenContract.Event.TappedOperationButton -> tappedOperationButton(event.operationType)
            is MainScreenContract.Event.TappedNumberButton -> tappedNumberButton(event.value)
            is MainScreenContract.Event.TappedDecimalButton -> tappedDecimalButton()
            is MainScreenContract.Event.TappedEqualButton -> tappedEqualButton()
            is MainScreenContract.Event.TappedClearButton -> tappedClearButton()
            is MainScreenContract.Event.TappedTab -> tappedTab(event.tabIndex)
        }
    }

    private fun tappedTab(tabIndex: Int) {
        if (tabIndex == 2) {
            setState(
                _uiState.value.copy(
                    numeralSystem = NumeralSystem.BINARY
                )
            )
        } else {
            setState(
                _uiState.value.copy(
                    numeralSystem = NumeralSystem.DECIMAL
                )
            )
        }
    }

    private fun tappedDecimalButton() {
        if (_uiState.value.powerOfTen == 0) {
            setState(
                _uiState.value.copy(
                    powerOfTen = -1
                )
            )
        }
    }

    private fun tappedEqualButton() {
        evaluate()
    }

    private fun tappedClearButton() {
        setState(
            _uiState.value.copy(
                firstOperation = true,
                currentOperation = null,
                value1 = 0F,
                value2 = 0F,
                powerOfTen = 0
            )
        )
    }

    private fun tappedOperationButton(operationType: OperationType) {
        setState(
            _uiState.value.copy(
                firstOperation = false,
                currentOperation = operationType,
                powerOfTen = 0
            )
        )
        if (_uiState.value.currentOperation is OperationType.UnaryOperationType) {
            evaluate()
        }
    }

    private fun tappedNumberButton(value: Float) {
        if (_uiState.value.powerOfTen < 0) {
            if (_uiState.value.firstOperation || _uiState.value.currentOperation == null) {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1 + value * 10F.pow(_uiState.value.powerOfTen),
                        powerOfTen = _uiState.value.powerOfTen - 1
                    )
                )
            } else {
                setState(
                    _uiState.value.copy(
                        value2 = _uiState.value.value2 + value * 10F.pow(_uiState.value.powerOfTen),
                        powerOfTen = _uiState.value.powerOfTen - 1
                    )
                )
            }
        } else {
            if (_uiState.value.firstOperation || _uiState.value.currentOperation == null) {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1 * 10 + value
                    )
                )
            } else {
                setState(
                    _uiState.value.copy(
                        value2 = _uiState.value.value2 * 10 + value
                    )
                )
            }
        }
    }

    private fun evaluate() {
        setState(
            _uiState.value.copy(
                powerOfTen = 0
            )
        )
        when (_uiState.value.currentOperation) {
            OperationType.BinaryOperationType.Addition -> {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1 + _uiState.value.value2,
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.BinaryOperationType.Division -> {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1 / _uiState.value.value2,
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.BinaryOperationType.DivisionInt -> {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1 / _uiState.value.value2,
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            is OperationType.BinaryOperationType.Logarithm -> {

            }

            OperationType.BinaryOperationType.Modulo -> {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1 % _uiState.value.value2,
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.BinaryOperationType.Multiplication -> {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1 * _uiState.value.value2,
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            is OperationType.BinaryOperationType.Power -> {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1.pow(_uiState.value.value2),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.BinaryOperationType.Subtraction -> {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1 - _uiState.value.value2,
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.AND -> {
                setState(
                    _uiState.value.copy(
                        value1 = (_uiState.value.value1.toInt() and _uiState.value.value2.toInt()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.NAND -> {
                setState(
                    _uiState.value.copy(
                        value1 = (_uiState.value.value1.toInt().inv() or _uiState.value.value2.toInt().inv()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.OR -> {
                setState(
                    _uiState.value.copy(
                        value1 = (_uiState.value.value1.toInt() or _uiState.value.value2.toInt()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.NOR -> {
                setState(
                    _uiState.value.copy(
                        value1 = (_uiState.value.value1.toInt() or _uiState.value.value2.toInt()).inv().toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.XOR -> {
                setState(
                    _uiState.value.copy(
                        value1 = (_uiState.value.value1.toInt() xor _uiState.value.value2.toInt()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.NOT -> {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1.toInt().inv().toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.SHIFT_L -> {
                setState(
                    _uiState.value.copy(
                        value1 = (_uiState.value.value1.toInt() shl _uiState.value.value2.toInt()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.SHIFT_R -> {
                setState(
                    _uiState.value.copy(
                        value1 = (_uiState.value.value1.toInt() shr _uiState.value.value2.toInt()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.UnaryOperationType.AbsoluteValue -> {
                setState(
                    _uiState.value.copy(
                        value1 = abs(_uiState.value.value1),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.UnaryOperationType.Ceil -> {
                setState(
                    _uiState.value.copy(
                        value1 = ceil(_uiState.value.value1),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.UnaryOperationType.Floor -> {
                setState(
                    _uiState.value.copy(
                        value1 = floor(_uiState.value.value1),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.UnaryOperationType.Sqrt -> {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1.pow(1F/2F),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.UnaryOperationType.Ln -> {
                setState(
                    _uiState.value.copy(
                        value1 = ln(_uiState.value.value1),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.UnaryOperationType.Log -> {
                setState(
                    _uiState.value.copy(
                        value1 = log10(_uiState.value.value1),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.UnaryOperationType.Square -> {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1.pow(2F),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            null -> {}
            OperationType.UnaryOperationType.Sin -> {
                val angleInRadians = convertToRadians(_uiState.value.value1.toDouble(), _uiState.value.angleMode)
                var result = sin(angleInRadians)
                if (abs(result) < 1e-12) result = 0.0 // Clean up small floating point inaccuracies near zero
                setState(
                    _uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true // Typically after a unary op, you start a new operation
                    )
                )
            }
            OperationType.UnaryOperationType.Cos -> {
                val angleInRadians = convertToRadians(_uiState.value.value1.toDouble(), _uiState.value.angleMode)
                var result = cos(angleInRadians)
                if (abs(result) < 1e-12) result = 0.0
                setState(
                    _uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true
                    )
                )
            }
            OperationType.UnaryOperationType.Tan -> {
                val angleInRadians = convertToRadians(_uiState.value.value1.toDouble(), _uiState.value.angleMode)
                val cosValue = cos(angleInRadians)
                var result: Double

                if (abs(cosValue) < 1e-12) { // Check for undefined cases (tan of 90 degrees, etc.)
                    result = Double.NaN // Tangent undefined
                } else {
                    result = tan(angleInRadians)
                    if (abs(result) < 1e-12) result = 0.0
                }
                setState(
                    _uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true
                    )
                )
            }
            OperationType.UnaryOperationType.Asin -> {
                val inputValue = _uiState.value.value1.toDouble()
                var result: Double

                if (inputValue < -1.0 || inputValue > 1.0) { // asin input must be between -1 and 1
                    result = Double.NaN // Domain error
                } else {
                    val resultInRadians = asin(inputValue)
                    result = convertFromRadians(resultInRadians, _uiState.value.angleMode)
                }
                setState(
                    _uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true
                    )
                )
            }
            OperationType.UnaryOperationType.Acos -> {
                val inputValue = _uiState.value.value1.toDouble()
                var result: Double

                if (inputValue < -1.0 || inputValue > 1.0) { // acos input must be between -1 and 1
                    result = Double.NaN // Domain error
                } else {
                    val resultInRadians = acos(inputValue)
                    result = convertFromRadians(resultInRadians, _uiState.value.angleMode)
                }
                setState(
                    _uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true
                    )
                )
            }
            OperationType.UnaryOperationType.Atan -> {
                val inputValue = _uiState.value.value1.toDouble()
                // atan input can be any real number, no domain check needed like asin/acos
                val resultInRadians = atan(inputValue)
                var result = convertFromRadians(resultInRadians, _uiState.value.angleMode)
                // No specific check for abs(result) < 1e-12 here as atan can be small and meaningful.
                setState(
                    _uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true
                    )
                )
            }
        }
    }

}