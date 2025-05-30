package com.example.calculator.models

data class Operation(
    val operationType: OperationType,
    val value: Float? = null
)