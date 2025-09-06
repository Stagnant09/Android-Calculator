package com.example.calculator.ui.screens.main.matrix

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.foundation.CustomViewModel
import com.example.calculator.models.Matrix
import com.example.calculator.models.MatrixOperationType
import com.example.calculator.utlis.add
import com.example.calculator.utlis.determinant
import com.example.calculator.utlis.inverse
import com.example.calculator.utlis.multiply
import com.example.calculator.utlis.scaleByFactor
import com.example.calculator.utlis.transpose
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MatrixScreenViewModel :
    CustomViewModel<MatrixScreenContract.State, MatrixScreenContract.Event, MatrixScreenContract.Effect>,
    ViewModel() {
    private var _uiState = MutableStateFlow(
        MatrixScreenContract.State(
            currentOperation = MatrixOperationType.Binary.AddMultiply
        )
    )
    val uiState: StateFlow<MatrixScreenContract.State> = _uiState.asStateFlow()

    private val _effect: Channel<MatrixScreenContract.Effect> = Channel()
    val uiEffect: Flow<MatrixScreenContract.Effect> = _effect.receiveAsFlow()

    fun setEffect(builder: () -> MatrixScreenContract.Effect) {
        val effectValue = builder()
        viewModelScope.launch {
            _effect.send(effectValue)
        }
    }

    override fun setState(state: MatrixScreenContract.State) {
        _uiState.value = state
    }

    override fun setEvent(event: MatrixScreenContract.Event) {
        handleEvent(event)
    }

    override fun handleEvent(event: MatrixScreenContract.Event) {
        when (event) {
            MatrixScreenContract.Event.TappedClearButton -> {

            }

            is MatrixScreenContract.Event.TappedEqualButton -> {
                when (_uiState.value.currentOperation) {
                    MatrixOperationType.Unary.Determinant -> {
                        val index = event.index
                        setState(
                            _uiState.value.copy(
                                impVal = determinant(if (index.toInt() == 0) _uiState.value.matrixA else _uiState.value.matrixB)
                            )
                        )
                        setEffect {
                            MatrixScreenContract.Effect.ShowResultModal(
                                operationType = _uiState.value.currentOperation,
                                matrix = _uiState.value.matrixC,
                                value = _uiState.value.impVal
                            )
                        }
                    }

                    MatrixOperationType.Unary.Inverse -> {
                        val index = event.index
                        setState(
                            _uiState.value.copy(
                                matrixC = if (index.toInt() == 0) inverse(_uiState.value.matrixA) else inverse(_uiState.value.matrixB)
                            )
                        )
                        setEffect {
                            MatrixScreenContract.Effect.ShowResultModal(
                                operationType = _uiState.value.currentOperation,
                                matrix = _uiState.value.matrixC,
                                value = _uiState.value.impVal
                            )
                        }
                    }

                    MatrixOperationType.Unary.Transpose -> {
                        val index = event.index
                        setState(
                            _uiState.value.copy(
                                matrixC = if (index.toInt() == 0) transpose(_uiState.value.matrixA) else transpose(_uiState.value.matrixB)
                            )
                        )
                        setEffect {
                            MatrixScreenContract.Effect.ShowResultModal(
                                operationType = _uiState.value.currentOperation,
                                matrix = _uiState.value.matrixC,
                                value = _uiState.value.impVal
                            )
                        }
                    }

                    else -> {}
                }
            }

            is MatrixScreenContract.Event.TappedEqualButtonAdd -> {
                Log.d("TappedEqualButtonAdd", "${_uiState.value.matrixA} \n ${_uiState.value.matrixB} \n ${_uiState.value.coefA} \n ${_uiState.value.coefB}")
                val newMatrix = add(
                    scaleByFactor(_uiState.value.matrixA, _uiState.value.coefA),
                    scaleByFactor(_uiState.value.matrixB, _uiState.value.coefB)
                )
                Log.d("TappedEqualButtonAdd", "$newMatrix")
                setState(
                    _uiState.value.copy(matrixC = newMatrix)
                )

                setEffect {
                    MatrixScreenContract.Effect.ShowResultModal(
                        operationType = _uiState.value.currentOperation,
                        matrix = newMatrix,
                        value = _uiState.value.impVal
                    )
                }
            }

            MatrixScreenContract.Event.TappedEqualButtonMultiply -> {
                val newMatrix = multiply(
                    scaleByFactor(_uiState.value.matrixA, _uiState.value.coefA),
                    scaleByFactor(_uiState.value.matrixB, _uiState.value.coefB)
                )

                setState(
                    _uiState.value.copy(matrixC = newMatrix)
                )

                setEffect {
                    MatrixScreenContract.Effect.ShowResultModal(
                        operationType = _uiState.value.currentOperation,
                        matrix = newMatrix,
                        value = _uiState.value.impVal
                    )
                }
            }

            is MatrixScreenContract.Event.TappedNumberField -> {
                val (matrix, row, column, value) = event
                when (matrix.toInt()) {
                    0 -> {
                        val newMatrix = _uiState.value.matrixA.copy().also { it[row, column] = value }
                        setState(_uiState.value.copy(matrixA = newMatrix))
                    }
                    1 -> {
                        val newMatrix = _uiState.value.matrixB.copy().also { it[row, column] = value }
                        setState(_uiState.value.copy(matrixB = newMatrix))
                    }
                }
            }

            is MatrixScreenContract.Event.TappedCoefField -> {
                val (index, value) = event
                when (index.toInt()) {
                    0 -> setState(
                        _uiState.value.copy(
                            coefA = value
                        )
                    )

                    1 -> setState(
                        _uiState.value.copy(
                            coefB = value
                        )
                    )
                }
            }

            is MatrixScreenContract.Event.TappedOperationButton -> {

            }

            is MatrixScreenContract.Event.TappedTopOperationButton -> {
                setState(
                    _uiState.value.copy(
                        currentOperation = event.operationType
                    )
                )
            }

            is MatrixScreenContract.Event.OpenMatrixDimensionsModal -> {
                setEffect {
                    MatrixScreenContract.Effect.ShowDimensionsModal(event.matrix)
                }
            }

            is MatrixScreenContract.Event.SetMatrixDimensions -> {
                val (matrix, rows, columns) = event
                when (matrix.toInt()) {
                    0 -> _uiState.value = _uiState.value.copy(matrixA = Matrix(rows, columns))
                    1 -> _uiState.value = _uiState.value.copy(matrixB = Matrix(rows, columns))
                }
            }
        }
    }

}