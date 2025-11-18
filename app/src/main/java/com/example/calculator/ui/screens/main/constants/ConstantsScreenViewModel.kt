package com.example.calculator.ui.screens.main.constants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.foundation.CustomViewModel
import com.example.calculator.models.constantsMathematics
import com.example.calculator.models.constantsScience
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ConstantsScreenViewModel : CustomViewModel<ConstantsScreenContract.State, ConstantsScreenContract.Event, ConstantsScreenContract.Effect>(
    initialState = ConstantsScreenContract.State()
) {

    override suspend fun handleEvent(event: ConstantsScreenContract.Event) {
        when (event) {
            ConstantsScreenContract.Event.TappedFloatingActionButton -> {
                setEffect {
                    ConstantsScreenContract.Effect.ShowDialog
                }
            }
            is ConstantsScreenContract.Event.TappedPositiveButton -> {
                applySearch(event.value)
            }
        }
    }

    private fun applySearch(query: String) {
        val results = constantsMathematics.filter {
            it.name.contains(query, true) || it.symbol.contains(query, true)
        } + constantsScience.filter {
            it.name.contains(query, true) || it.symbol.contains(query, true)
        }
        setState(
            uiState.value.copy(
                results = results
            )
        )
        setEffect {
            ConstantsScreenContract.Effect.ShowResults
        }
    }

}