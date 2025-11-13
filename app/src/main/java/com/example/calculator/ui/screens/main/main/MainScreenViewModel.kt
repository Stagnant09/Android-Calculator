package com.example.calculator.ui.screens.main.main

import androidx.lifecycle.ViewModel
import com.example.calculator.foundation.CustomViewModel
import com.example.calculator.models.AngleMode
import com.example.calculator.models.NumeralSystem
import com.example.calculator.models.OperationType
import com.example.calculator.ui.screens.main.main.MainScreenContract
import com.example.calculator.utlis.convertFromRadians
import com.example.calculator.utlis.convertToRadians
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.E
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.log
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.tan

class MainScreenViewmodel : CustomViewModel<MainScreenContract.State, MainScreenContract.Event, MainScreenContract.Effect>, ViewModel() {

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
            is MainScreenContract.Event.TappedConstantButton -> tappedConstant(event.value)
            is MainScreenContract.Event.TappedDecimalButton -> tappedDecimalButton()
            is MainScreenContract.Event.TappedEqualButton -> tappedEqualButton()
            is MainScreenContract.Event.TappedClearButton -> tappedClearButton()
            is MainScreenContract.Event.TappedTab -> tappedTab(event.tabIndex)
            is MainScreenContract.Event.TappedAngleModeButton -> tappedAngleModeButton()
            is MainScreenContract.Event.TappedAlternativeButton -> tappedAlternativeButton()
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

    private fun tappedAngleModeButton(){
        val _angleMode = when (_uiState.value.angleMode) {
            AngleMode.DEGREES -> AngleMode.RADIANS
            AngleMode.RADIANS -> AngleMode.DEGREES
        }
        setState(
            _uiState.value.copy(
                angleMode = _angleMode
            )
        )
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
                powerOfTen = 0,
                customHeader = ""
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

    private fun tappedConstant(value: Float) {
        val state = _uiState.value

        if (state.firstOperation || state.currentOperation == null) {
            setState(
                state.copy(
                    value1 = value,
                    value2 = 0F,
                    powerOfTen = 0,
                    firstOperation = true
                )
            )
        } else {
            // Operation in progress → replace the second number
            setState(
                state.copy(
                    value2 = value,
                    powerOfTen = 0
                )
            )
        }
    }

    private fun evaluate() {
        setState(
            _uiState.value.copy(
                powerOfTen = 0
            )
        )
        if (_uiState.value.currentOperation is OperationType.BinaryOperationType) {
            setState(_uiState.value.copy(customHeader = ""))
        }
        when (_uiState.value.currentOperation) {
            OperationType.BinaryOperationType.Addition -> {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1 + _uiState.value.value2,
                        value2 = 0F,
                        currentOperation = null,
                    )
                )
            }

            OperationType.BinaryOperationType.Division -> {
                // Added check for division by zero
                if (_uiState.value.value2 != 0F) {
                    setState(
                        _uiState.value.copy(
                            value1 = _uiState.value.value1 / _uiState.value.value2,
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                } else {
                    // Handle division by zero error, e.g., by setting value1 to NaN or infinity
                    setState(
                        _uiState.value.copy(
                            value1 = Float.NaN,
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                }
            }

            OperationType.BinaryOperationType.DivisionInt -> {
                setState(
                    _uiState.value.copy(
                        value1 = (_uiState.value.value1.toInt() / _uiState.value.value2.toInt()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            is OperationType.BinaryOperationType.Logarithm -> {
                // Calculates log base 'value2' of 'value1'
                if (_uiState.value.value1 > 0 && _uiState.value.value2 > 0 && _uiState.value.value2 != 1F) {
                    setState(
                        _uiState.value.copy(
                            value1 = log(_uiState.value.value1, _uiState.value.value2),
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                } else {
                    setState(
                        _uiState.value.copy(
                            value1 = Float.NaN, // Domain error for logarithm
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                }
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
                // Check for negative input
                if (_uiState.value.value1 >= 0F) {
                    setState(
                        _uiState.value.copy(
                            value1 = _uiState.value.value1.pow(1F/2F),
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                } else {
                    setState(
                        _uiState.value.copy(
                            value1 = Float.NaN, // Domain error
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                }
            }

            OperationType.UnaryOperationType.Ln -> {
                // Check for non-positive input
                if (_uiState.value.value1 > 0F) {
                    setState(
                        _uiState.value.copy(
                            value1 = ln(_uiState.value.value1),
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                } else {
                    setState(
                        _uiState.value.copy(
                            value1 = Float.NaN, // Domain error
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                }
            }

            OperationType.UnaryOperationType.Log -> {
                // Check for non-positive input
                if (_uiState.value.value1 > 0F) {
                    setState(
                        _uiState.value.copy(
                            value1 = log10(_uiState.value.value1),
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                } else {
                    setState(
                        _uiState.value.copy(
                            value1 = Float.NaN, // Domain error
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                }
            }

            OperationType.UnaryOperationType.Square -> {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1.pow(2F),
                        value2 = 0F,
                        currentOperation = null,
                        customHeader = ""
                    )
                )
            }

            OperationType.UnaryOperationType.Cube -> {
                setState(
                    _uiState.value.copy(
                        value1 = _uiState.value.value1.pow(3F),
                        value2 = 0F,
                        currentOperation = null,
                        customHeader = ""
                    )
                )
            }

            OperationType.UnaryOperationType.Factorial -> {
                val inputValue = _uiState.value.value1.toInt()
                if (inputValue >= 0) {
                    val result = factorial(inputValue)
                    setState(
                        _uiState.value.copy(
                            value1 = result.toFloat(),
                            value2 = 0F,
                            currentOperation = null,
                            customHeader = ""
                        )
                    )
                } else {
                    // Factorial of a negative number is undefined
                    setState(
                        _uiState.value.copy(
                            value1 = Float.NaN,
                            value2 = 0F,
                            currentOperation = null,
                            customHeader = ""
                        )
                    )
                }
            }

            OperationType.UnaryOperationType.Reciprocal -> {
                // Check for reciprocal of zero
                if (_uiState.value.value1 != 0F) {
                    setState(
                        _uiState.value.copy(
                            value1 = 1F / _uiState.value.value1,
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                } else {
                    setState(
                        _uiState.value.copy(
                            value1 = Float.NaN, // Division by zero
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                }
            }

            OperationType.Constant.E -> {
                val currentState = _uiState.value
                val constantValue = E.toFloat()

                if (currentState.firstOperation || currentState.currentOperation == null) {
                    setState(
                        currentState.copy(
                            value1 = constantValue,
                            value2 = 0F,
                            firstOperation = true,
                            powerOfTen = 0
                        )
                    )
                } else {
                    setState(
                        currentState.copy(
                            value2 = constantValue,
                            powerOfTen = 0
                        )
                    )
                }
            }

            OperationType.Constant.Pi -> {
                val currentState = _uiState.value
                val constantValue = PI.toFloat()

                if (currentState.firstOperation || currentState.currentOperation == null) {
                    setState(
                        currentState.copy(
                            value1 = constantValue,
                            value2 = 0F,
                            firstOperation = true,
                            powerOfTen = 0
                        )
                    )
                } else {
                    setState(
                        currentState.copy(
                            value2 = constantValue,
                            powerOfTen = 0
                        )
                    )
                }
            }

            OperationType.UnaryOperationType.Sin -> {
                val angleInRadians = convertToRadians(_uiState.value.value1.toDouble(), _uiState.value.angleMode)
                var result = sin(angleInRadians)
                if (abs(result) < 1e-12) result = 0.0
                val header = "sin(${angleInRadians}) ="
                setState(
                    _uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true,
                        customHeader = header
                    )
                )
            }
            OperationType.UnaryOperationType.Cos -> {
                val angleInRadians = convertToRadians(_uiState.value.value1.toDouble(), _uiState.value.angleMode)
                var result = cos(angleInRadians)
                if (abs(result) < 1e-12) result = 0.0
                val header = "cos(${angleInRadians}) ="
                setState(
                    _uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true,
                        customHeader = header
                    )
                )
            }
            OperationType.UnaryOperationType.Tan -> {
                val angleInRadians = convertToRadians(_uiState.value.value1.toDouble(), _uiState.value.angleMode)
                val cosValue = cos(angleInRadians)
                var result: Double
                val header = "tan(${angleInRadians}) ="
                if (abs(cosValue) < 1e-12) {
                    result = Double.NaN
                } else {
                    result = tan(angleInRadians)
                    if (abs(result) < 1e-12) result = 0.0
                }
                setState(
                    _uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true,
                        customHeader = header
                    )
                )
            }
            OperationType.UnaryOperationType.Cot -> {
                val angleInRadians = convertToRadians(_uiState.value.value1.toDouble(), _uiState.value.angleMode)
                val sinValue = sin(angleInRadians)
                var result: Double
                val header = "cot(${angleInRadians}) ="
                if (abs(sinValue) < 1e-12) {
                    result = Double.NaN // Cotangent is undefined
                } else {
                    result = 1.0 / tan(angleInRadians)
                    if (abs(result) < 1e-12) result = 0.0
                }
                setState(
                    _uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true,
                        customHeader = header
                    )
                )
            }
            OperationType.UnaryOperationType.Asin -> {
                val inputValue = _uiState.value.value1.toDouble()
                var result: Double

                if (inputValue < -1.0 || inputValue > 1.0) {
                    result = Double.NaN
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

                if (inputValue < -1.0 || inputValue > 1.0) {
                    result = Double.NaN
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
                val resultInRadians = atan(inputValue)
                var result = convertFromRadians(resultInRadians, _uiState.value.angleMode)
                setState(
                    _uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true
                    )
                )
            }
            OperationType.UnaryOperationType.Acot -> {
                val inputValue = _uiState.value.value1.toDouble()
                val resultInRadians = PI / 2.0 - atan(inputValue)
                var result = convertFromRadians(resultInRadians, _uiState.value.angleMode)
                setState(
                    _uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true
                    )
                )
            }
            // These operations are handled by other functions and should not be here
            OperationType.Alternative -> {}
            OperationType.CloseParenthesis -> {}
            OperationType.Mode -> {

            }
            OperationType.OpenParenthesis -> {}
            null -> {}
            else -> {}
        }
    }

    private fun tappedAlternativeButton() {
        setState(
            _uiState.value.copy(
                alt = !_uiState.value.alt
            )
        )
    }

    // A helper function to calculate the factorial
    private fun factorial(n: Int): Long {
        return if (n <= 1) {
            1L
        } else {
            n.toLong() * factorial(n - 1)
        }

    }

}