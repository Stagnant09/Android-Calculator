package com.example.calculator.repository

import com.example.calculator.network.CurrencyApi
import com.example.calculator.network.RetrofitProvider

object CurrencyRepository {
    private val api = RetrofitProvider.retrofit.create(CurrencyApi::class.java)

    suspend fun fetchRates(base: String = "USD"): Map<String, Double> {
        val response = api.getRates(base)
        return response.rates
    }
}
