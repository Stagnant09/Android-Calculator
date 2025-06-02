package com.example.calculator.ui.screens.main

 import androidx.lifecycle.ViewModel
import com.example.calculator.models.OperationType
import com.example.calculator.ui.screens.main.components.MainScreenContract
import com.example.calculator.foundation.CustomViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.pow

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

            }

            OperationType.LogicOperationType.NAND -> {

            }

            OperationType.LogicOperationType.NOR -> {

            }

            OperationType.LogicOperationType.NOT -> {

            }

            OperationType.LogicOperationType.OR -> {

            }

            OperationType.LogicOperationType.SHIFT_L -> {

            }

            OperationType.LogicOperationType.SHIFT_R -> {

            }

            OperationType.LogicOperationType.XOR -> {

            }

            OperationType.UnaryOperationType.AbsoluteValue -> {

            }

            OperationType.UnaryOperationType.Ceil -> {

            }

            OperationType.UnaryOperationType.Floor -> {

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

            null -> {

            }

        }
    }

}