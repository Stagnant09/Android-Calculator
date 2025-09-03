package com.example.calculator.utlis

import com.example.calculator.models.AngleMode
import com.example.calculator.models.Matrix
import com.example.calculator.models.OperationType
import kotlin.math.PI
import kotlin.math.acos
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

/** Function that given a triangle with sides a, b, c returns the angles between all sides.
 */
fun getTriangleAngles(a: Double, b: Double, c: Double): Triple<Double, Double, Double> {
    val angle1 = acos((b.pow(2) + c.pow(2) - a.pow(2)) / (2 * b * c)) // in radians
    val angle2 = acos((a.pow(2) + c.pow(2) - b.pow(2)) / (2 * a * c)) // in radians
    val angle3 = acos((a.pow(2) + b.pow(2) - c.pow(2)) / (2 * a * b)) // in radians
    return Triple(angle1, angle2, angle3)
}

/** Matrix addition */
fun add(A: Matrix, B: Matrix): Matrix {
    require(A.rows == B.rows && A.columns == B.columns) { "Matrix dimensions must match" }

    val result = Matrix(A.rows, A.columns)
    for (i in 0 until A.rows) {
        for (j in 0 until A.columns) {
            result[i, j] = A[i, j] + B[i, j]
        }
    }
    return result
}

/** Matrix multiplication */
fun multiply(A: Matrix, B: Matrix): Matrix {
    require(A.columns == B.rows) { "Number of columns of A must equal rows of B" }

    val result = Matrix(A.rows, B.columns)
    for (i in 0 until A.rows) {
        for (j in 0 until B.columns) {
            var sum = 0f
            for (k in 0 until A.columns) {
                sum += A[i, k] * B[k, j]
            }
            result[i, j] = sum
        }
    }
    return result
}

/** Determinant (recursive, Laplace expansion) */
fun determinant(M: Matrix): Float {
    require(M.rows == M.columns) { "Determinant is defined only for square matrices" }

    // Base cases
    if (M.rows == 1) return M[0, 0]
    if (M.rows == 2) return M[0, 0] * M[1, 1] - M[0, 1] * M[1, 0]

    var det = 0f
    for (col in 0 until M.columns) {
        det += ((if (col % 2 == 0) 1 else -1) * M[0, col] * determinant(minor(M, 0, col)))
    }
    return det
}

/** Helper: minor matrix (remove row and col) */
fun minor(M: Matrix, row: Int, col: Int): Matrix {
    val elements = mutableListOf<MutableList<Float>>()
    for (i in 0 until M.rows) {
        if (i == row) continue
        val newRow = mutableListOf<Float>()
        for (j in 0 until M.columns) {
            if (j == col) continue
            newRow.add(M[i, j])
        }
        elements.add(newRow)
    }
    return Matrix(M.rows - 1, M.columns - 1, elements)
}
