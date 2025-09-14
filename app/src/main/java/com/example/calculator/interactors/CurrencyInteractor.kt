package com.example.calculator.interactors

import com.example.calculator.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class CurrencyInteractor(
    private val repository: CurrencyRepository = CurrencyRepository
) {
    fun getRates(base: String): Flow<Result<Map<String, Double>>> = flow {
        emit(Result.success(repository.fetchRates(base)))
    }
        .catch { e -> emit(Result.failure(e)) }
        .flowOn(Dispatchers.IO)
}