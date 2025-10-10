package com.example.calculator.models

data class DualOperation(
    val normal: OperationType,
    val alt: OperationType,
    val normalLabel: String,
    val altLabel: String
)