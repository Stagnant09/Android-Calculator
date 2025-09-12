package com.example.calculator.models

data class CurrencyUnit(
    val name: String,
    val code: String, // 3-letter code (USD, EUR, etc.)
    val symbol: String,
    val flag: String
)