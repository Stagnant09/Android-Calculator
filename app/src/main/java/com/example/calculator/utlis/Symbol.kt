package com.example.calculator.utlis

import com.example.calculator.models.OperationType

interface Term

/** A symbol is a mathematical object that represents some variable in some expression
 */
class Symbol : Term {
    var value: String
    var type: SymbolType

    constructor(value: String, type: SymbolType) {
        this.value = value
        this.type = type
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Symbol) return false
        return value == other.value && type == other.type
    }

    override fun hashCode(): Int {
        return value.hashCode() * 31 + type.hashCode()
    }
}

sealed class SymbolType {
    class IndependentCartesianVariable : SymbolType()
    class DependentCartesianVariable : SymbolType()
    class Constant : SymbolType()
    class IndependentPolarVariable : SymbolType()
    class DependentPolarVariable : SymbolType()
}

data class Operation(
    var type: OperationType
) : Term