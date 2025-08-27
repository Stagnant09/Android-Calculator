package com.example.calculator.utlis

import com.example.calculator.models.AngleMode
import com.example.calculator.models.OperationType
import kotlin.math.PI
import kotlin.math.pow

fun symbol(operationType: OperationType): String = when (operationType) {
    OperationType.BinaryOperationType.Addition -> "+"
    OperationType.BinaryOperationType.Division -> "/"
    OperationType.BinaryOperationType.DivisionInt -> "/"
    OperationType.BinaryOperationType.Multiplication -> "*"
    OperationType.BinaryOperationType.Subtraction -> "-"
    OperationType.BinaryOperationType.Modulo -> "%"
    OperationType.BinaryOperationType.Power -> "^"
    else -> ""
}

fun Int.toBinary(): String {
    var n = this
    var result = ""
    for (i in findNearestPowerOfTwo(n) downTo 0) {
        if (n >= 2.0.pow(i.toDouble()).toInt()) {
            result += "1"
            n -= 2.0.pow(i.toDouble()).toInt()
        } else {
            result += "0"
        }
    }
    return result
}

fun findNearestPowerOfTwo(n: Int): Int { // e.g. 1025 -> 1024
    var i = 0
    while (2.0.pow(i.toDouble()).toInt() <= n) {
        i++
    }
    return i - 1
}


// Function to convert from current angleMode to Radians
fun convertToRadians(value: Double, mode: AngleMode): Double {
    return when (mode) {
        AngleMode.DEGREES -> Math.toRadians(value)
        AngleMode.RADIANS -> value
    }
}

// Function to convert from Radians to current angleMode
fun convertFromRadians(value: Double, mode: AngleMode): Double {
    return when (mode) {
        AngleMode.DEGREES -> Math.toDegrees(value)
        AngleMode.RADIANS -> value
    }
}