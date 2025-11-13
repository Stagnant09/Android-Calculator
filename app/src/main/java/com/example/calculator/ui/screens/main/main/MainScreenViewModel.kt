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

class MainScreenViewmodel : CustomViewModel<MainScreenContract.State, MainScreenContract.Event, MainScreenContract.Effect>(
    initialState = MainScreenContract.State()
) {

    override suspend fun handleEvent(event: MainScreenContract.Event) {
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
                uiState.value.copy(
                    numeralSystem = NumeralSystem.BINARY
                )
            )
        } else {
            setState(
                uiState.value.copy(
                    numeralSystem = NumeralSystem.DECIMAL
                )
            )
        }
    }

    private fun tappedAngleModeButton(){
        val _angleMode = when (uiState.value.angleMode) {
            AngleMode.DEGREES -> AngleMode.RADIANS
            AngleMode.RADIANS -> AngleMode.DEGREES
        }
        setState(
            uiState.value.copy(
                angleMode = _angleMode
            )
        )
    }

    private fun tappedDecimalButton() {
        if (uiState.value.powerOfTen == 0) {
            setState(
                uiState.value.copy(
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
            uiState.value.copy(
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
            uiState.value.copy(
                firstOperation = false,
                currentOperation = operationType,
                powerOfTen = 0
            )
        )
        if (uiState.value.currentOperation is OperationType.UnaryOperationType) {
            evaluate()
        }
    }

    private fun tappedNumberButton(value: Float) {
        if (uiState.value.powerOfTen < 0) {
            if (uiState.value.firstOperation || uiState.value.currentOperation == null) {
                setState(
                    uiState.value.copy(
                        value1 = uiState.value.value1 + value * 10F.pow(uiState.value.powerOfTen),
                        powerOfTen = uiState.value.powerOfTen - 1
                    )
                )
            } else {
                setState(
                    uiState.value.copy(
                        value2 = uiState.value.value2 + value * 10F.pow(uiState.value.powerOfTen),
                        powerOfTen = uiState.value.powerOfTen - 1
                    )
                )
            }
        } else {
            if (uiState.value.firstOperation || uiState.value.currentOperation == null) {
                setState(
                    uiState.value.copy(
                        value1 = uiState.value.value1 * 10 + value
                    )
                )
            } else {
                setState(
                    uiState.value.copy(
                        value2 = uiState.value.value2 * 10 + value
                    )
                )
            }
        }
    }

    private fun tappedConstant(value: Float) {
        val state = uiState.value

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
            uiState.value.copy(
                powerOfTen = 0
            )
        )
        if (uiState.value.currentOperation is OperationType.BinaryOperationType) {
            setState(uiState.value.copy(customHeader = ""))
        }
        when (uiState.value.currentOperation) {
            OperationType.BinaryOperationType.Addition -> {
                setState(
                    uiState.value.copy(
                        value1 = uiState.value.value1 + uiState.value.value2,
                        value2 = 0F,
                        currentOperation = null,
                    )
                )
            }

            OperationType.BinaryOperationType.Division -> {
                // Added check for division by zero
                if (uiState.value.value2 != 0F) {
                    setState(
                        uiState.value.copy(
                            value1 = uiState.value.value1 / uiState.value.value2,
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                } else {
                    // Handle division by zero error, e.g., by setting value1 to NaN or infinity
                    setState(
                        uiState.value.copy(
                            value1 = Float.NaN,
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                }
            }

            OperationType.BinaryOperationType.DivisionInt -> {
                setState(
                    uiState.value.copy(
                        value1 = (uiState.value.value1.toInt() / uiState.value.value2.toInt()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            is OperationType.BinaryOperationType.Logarithm -> {
                // Calculates log base 'value2' of 'value1'
                if (uiState.value.value1 > 0 && uiState.value.value2 > 0 && uiState.value.value2 != 1F) {
                    setState(
                        uiState.value.copy(
                            value1 = log(uiState.value.value1, uiState.value.value2),
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                } else {
                    setState(
                        uiState.value.copy(
                            value1 = Float.NaN, // Domain error for logarithm
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                }
            }

            OperationType.BinaryOperationType.Modulo -> {
                setState(
                    uiState.value.copy(
                        value1 = uiState.value.value1 % uiState.value.value2,
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.BinaryOperationType.Multiplication -> {
                setState(
                    uiState.value.copy(
                        value1 = uiState.value.value1 * uiState.value.value2,
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            is OperationType.BinaryOperationType.Power -> {
                setState(
                    uiState.value.copy(
                        value1 = uiState.value.value1.pow(uiState.value.value2),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.BinaryOperationType.Subtraction -> {
                setState(
                    uiState.value.copy(
                        value1 = uiState.value.value1 - uiState.value.value2,
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.AND -> {
                setState(
                    uiState.value.copy(
                        value1 = (uiState.value.value1.toInt() and uiState.value.value2.toInt()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.NAND -> {
                setState(
                    uiState.value.copy(
                        value1 = (uiState.value.value1.toInt().inv() or uiState.value.value2.toInt().inv()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.OR -> {
                setState(
                    uiState.value.copy(
                        value1 = (uiState.value.value1.toInt() or uiState.value.value2.toInt()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.NOR -> {
                setState(
                    uiState.value.copy(
                        value1 = (uiState.value.value1.toInt() or uiState.value.value2.toInt()).inv().toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.XOR -> {
                setState(
                    uiState.value.copy(
                        value1 = (uiState.value.value1.toInt() xor uiState.value.value2.toInt()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.NOT -> {
                setState(
                    uiState.value.copy(
                        value1 = uiState.value.value1.toInt().inv().toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.SHIFT_L -> {
                setState(
                    uiState.value.copy(
                        value1 = (uiState.value.value1.toInt() shl uiState.value.value2.toInt()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.LogicOperationType.SHIFT_R -> {
                setState(
                    uiState.value.copy(
                        value1 = (uiState.value.value1.toInt() shr uiState.value.value2.toInt()).toFloat(),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.UnaryOperationType.AbsoluteValue -> {
                setState(
                    uiState.value.copy(
                        value1 = abs(uiState.value.value1),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.UnaryOperationType.Ceil -> {
                setState(
                    uiState.value.copy(
                        value1 = ceil(uiState.value.value1),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.UnaryOperationType.Floor -> {
                setState(
                    uiState.value.copy(
                        value1 = floor(uiState.value.value1),
                        value2 = 0F,
                        currentOperation = null
                    )
                )
            }

            OperationType.UnaryOperationType.Sqrt -> {
                // Check for negative input
                if (uiState.value.value1 >= 0F) {
                    setState(
                        uiState.value.copy(
                            value1 = uiState.value.value1.pow(1F/2F),
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                } else {
                    setState(
                        uiState.value.copy(
                            value1 = Float.NaN, // Domain error
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                }
            }

            OperationType.UnaryOperationType.Ln -> {
                // Check for non-positive input
                if (uiState.value.value1 > 0F) {
                    setState(
                        uiState.value.copy(
                            value1 = ln(uiState.value.value1),
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                } else {
                    setState(
                        uiState.value.copy(
                            value1 = Float.NaN, // Domain error
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                }
            }

            OperationType.UnaryOperationType.Log -> {
                // Check for non-positive input
                if (uiState.value.value1 > 0F) {
                    setState(
                        uiState.value.copy(
                            value1 = log10(uiState.value.value1),
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                } else {
                    setState(
                        uiState.value.copy(
                            value1 = Float.NaN, // Domain error
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                }
            }

            OperationType.UnaryOperationType.Square -> {
                setState(
                    uiState.value.copy(
                        value1 = uiState.value.value1.pow(2F),
                        value2 = 0F,
                        currentOperation = null,
                        customHeader = ""
                    )
                )
            }

            OperationType.UnaryOperationType.Cube -> {
                setState(
                    uiState.value.copy(
                        value1 = uiState.value.value1.pow(3F),
                        value2 = 0F,
                        currentOperation = null,
                        customHeader = ""
                    )
                )
            }

            OperationType.UnaryOperationType.Factorial -> {
                val inputValue = uiState.value.value1.toInt()
                if (inputValue >= 0) {
                    val result = factorial(inputValue)
                    setState(
                        uiState.value.copy(
                            value1 = result.toFloat(),
                            value2 = 0F,
                            currentOperation = null,
                            customHeader = ""
                        )
                    )
                } else {
                    // Factorial of a negative number is undefined
                    setState(
                        uiState.value.copy(
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
                if (uiState.value.value1 != 0F) {
                    setState(
                        uiState.value.copy(
                            value1 = 1F / uiState.value.value1,
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                } else {
                    setState(
                        uiState.value.copy(
                            value1 = Float.NaN, // Division by zero
                            value2 = 0F,
                            currentOperation = null
                        )
                    )
                }
            }

            OperationType.Constant.E -> {
                val currentState = uiState.value
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
                val currentState = uiState.value
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
                val angleInRadians = convertToRadians(uiState.value.value1.toDouble(), uiState.value.angleMode)
                var result = sin(angleInRadians)
                if (abs(result) < 1e-12) result = 0.0
                val header = "sin(${angleInRadians}) ="
                setState(
                    uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true,
                        customHeader = header
                    )
                )
            }
            OperationType.UnaryOperationType.Cos -> {
                val angleInRadians = convertToRadians(uiState.value.value1.toDouble(), uiState.value.angleMode)
                var result = cos(angleInRadians)
                if (abs(result) < 1e-12) result = 0.0
                val header = "cos(${angleInRadians}) ="
                setState(
                    uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true,
                        customHeader = header
                    )
                )
            }
            OperationType.UnaryOperationType.Tan -> {
                val angleInRadians = convertToRadians(uiState.value.value1.toDouble(), uiState.value.angleMode)
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
                    uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true,
                        customHeader = header
                    )
                )
            }
            OperationType.UnaryOperationType.Cot -> {
                val angleInRadians = convertToRadians(uiState.value.value1.toDouble(), uiState.value.angleMode)
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
                    uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true,
                        customHeader = header
                    )
                )
            }
            OperationType.UnaryOperationType.Asin -> {
                val inputValue = uiState.value.value1.toDouble()
                var result: Double

                if (inputValue < -1.0 || inputValue > 1.0) {
                    result = Double.NaN
                } else {
                    val resultInRadians = asin(inputValue)
                    result = convertFromRadians(resultInRadians, uiState.value.angleMode)
                }
                setState(
                    uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true
                    )
                )
            }
            OperationType.UnaryOperationType.Acos -> {
                val inputValue = uiState.value.value1.toDouble()
                var result: Double

                if (inputValue < -1.0 || inputValue > 1.0) {
                    result = Double.NaN
                } else {
                    val resultInRadians = acos(inputValue)
                    result = convertFromRadians(resultInRadians, uiState.value.angleMode)
                }
                setState(
                    uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true
                    )
                )
            }
            OperationType.UnaryOperationType.Atan -> {
                val inputValue = uiState.value.value1.toDouble()
                val resultInRadians = atan(inputValue)
                var result = convertFromRadians(resultInRadians, uiState.value.angleMode)
                setState(
                    uiState.value.copy(
                        value1 = result.toFloat(),
                        value2 = 0F,
                        currentOperation = null,
                        firstOperation = true
                    )
                )
            }
            OperationType.UnaryOperationType.Acot -> {
                val inputValue = uiState.value.value1.toDouble()
                val resultInRadians = PI / 2.0 - atan(inputValue)
                var result = convertFromRadians(resultInRadians, uiState.value.angleMode)
                setState(
                    uiState.value.copy(
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
            uiState.value.copy(
                alt = !uiState.value.alt
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