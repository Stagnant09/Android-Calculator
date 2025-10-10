package com.example.calculator.models

sealed class CombinatoricsOperationType {

    sealed class NoRepetition {
        object Permutations
        object Combinations
    }

    sealed class Repetition {
        object Permutations
        object Combinations
    }

}