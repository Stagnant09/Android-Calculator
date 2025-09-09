package com.example.calculator.network

import retrofit2.http.GET
import retrofit2.http.Query

data class CurrencyResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)

interface CurrencyApi {
    @GET("latest")
    suspend fun getRates(
        @Query("from") base: String = "USD"
    ): CurrencyResponse
}
