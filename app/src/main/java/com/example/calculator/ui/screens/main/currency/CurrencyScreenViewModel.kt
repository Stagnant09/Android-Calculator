package com.example.calculator.ui.screens.main.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.foundation.CustomViewModel
import com.example.calculator.interactors.CurrencyInteractor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CurrencyScreenViewModel(
    private val interactor: CurrencyInteractor = CurrencyInteractor()
) : CustomViewModel<CurrencyScreenContract.State, CurrencyScreenContract.Event, CurrencyScreenContract.Effect>, ViewModel() {

    private var _uiState = MutableStateFlow(
        CurrencyScreenContract.State()
    )
    val uiState: StateFlow<CurrencyScreenContract.State> = _uiState.asStateFlow()

    private val _effect: Channel<CurrencyScreenContract.Effect> = Channel()
    val uiEffect: Flow<CurrencyScreenContract.Effect> = _effect.receiveAsFlow()

    fun setEffect(builder: () -> CurrencyScreenContract.Effect) {
        val effectValue = builder()
        viewModelScope.launch {
            _effect.send(effectValue)
        }
    }

    override fun setState(state: CurrencyScreenContract.State) {
        _uiState.value = state
    }

    override fun setEvent(event: CurrencyScreenContract.Event) {
        handleEvent(event)
    }

    override fun handleEvent(event: CurrencyScreenContract.Event) {
        when (event) {
            CurrencyScreenContract.Event.Init -> init()
            is CurrencyScreenContract.Event.OnInputChanged -> onInputChanged(event)
            CurrencyScreenContract.Event.Refresh -> init()
        }
    }

    private fun init(baseCurrency: String = "EUR") {
        viewModelScope.launch {
            interactor.getRates(baseCurrency)
                .collect { result ->
                    result.fold(
                        onSuccess = { rates ->
                            // Transform rates to Floats for UI display
                            val exchangeValues = rates.values.map { it.toFloat() }

                            setState(_uiState.value.copy(
                                currencyExchangeValues = listOf(1f) + exchangeValues, // value of 1 for the base currency
                                currencyDisplayValues = listOf(1f) + exchangeValues // initially same
                            ))
                        },
                        onFailure = { error ->
                            println("Failed to fetch currency rates: ${error.message}")
                        }
                    )
                }
        }
    }

    private fun onInputChanged(event: CurrencyScreenContract.Event.OnInputChanged) {
        val inputAmount = event.input.toFloatOrNull() ?: 0f

        val baseRate = _uiState.value.currencyExchangeValues.getOrNull(event.index)
        if (baseRate == null || baseRate == 0f) return // avoid division by zero
        // Convert input back to base currency first
        val amountInBase = inputAmount / baseRate
        // Then recalculate all display values relative to base
        val newDisplayValues = _uiState.value.currencyExchangeValues.map { rate ->
            amountInBase * rate
        }

        setState(_uiState.value.copy(
            currencyDisplayValues = newDisplayValues
        ))
    }


}