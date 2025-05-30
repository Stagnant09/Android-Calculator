package com.example.calculator.ui.screens.main

import androidx.compose.runtime.mutableFloatStateOf
import androidx.lifecycle.ViewModel
import com.example.calculator.models.Operation
import com.example.calculator.models.OperationType
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow

class MainScreenViewmodel : ViewModel() {

    private val _currentValue = mutableFloatStateOf(0F)
    val currentValue = _currentValue.floatValue

    fun tappedButton(operationType: OperationType, value: Float) {
        evaluate(
            operation = Operation(
                operationType = operationType,
                value = value
            )
        )
    }

    private fun evaluate(operation: Operation) {
        when (operation.operationType) {
            OperationType.BinaryOperationType.Addition -> {
                val newValue = _currentValue.floatValue + operation.value!!
                _currentValue.floatValue = newValue
            }

            OperationType.BinaryOperationType.Division -> {
                val newValue = _currentValue.floatValue / operation.value!!
                _currentValue.floatValue = newValue
            }

            OperationType.BinaryOperationType.DivisionInt -> {
                val newValue = _currentValue.floatValue / operation.value!!
                _currentValue.floatValue = newValue
            }

            is OperationType.BinaryOperationType.Logarithm -> {}
            OperationType.BinaryOperationType.Modulo -> {
                val newValue = _currentValue.floatValue % operation.value!!
                _currentValue.floatValue = newValue
            }

            OperationType.BinaryOperationType.Multiplication -> {
                val newValue = _currentValue.floatValue * operation.value!!
                _currentValue.floatValue = newValue
            }

            is OperationType.BinaryOperationType.Power -> {
                val newValue = _currentValue.floatValue.pow(operation.value!!)
                _currentValue.floatValue = newValue
            }

            OperationType.BinaryOperationType.Subtraction -> {
                val newValue = _currentValue.floatValue - operation.value!!
                _currentValue.floatValue = newValue
            }

            OperationType.LogicOperationType.AND -> {}
            OperationType.LogicOperationType.NAND -> {}
            OperationType.LogicOperationType.NOR -> {}
            OperationType.LogicOperationType.NOT -> {}
            OperationType.LogicOperationType.OR -> {}
            OperationType.LogicOperationType.SHIFT_L -> {}
            OperationType.LogicOperationType.SHIFT_R -> {}
            OperationType.LogicOperationType.XOR -> {}
            OperationType.UnaryOperationType.AbsoluteValue -> {
                _currentValue.floatValue = abs(_currentValue.floatValue)
            }

            OperationType.UnaryOperationType.Ceil -> {
                _currentValue.floatValue = ceil(_currentValue.floatValue)
            }

            OperationType.UnaryOperationType.Floor -> {
                _currentValue.floatValue = floor(_currentValue.floatValue)
            }
        }
    }

}