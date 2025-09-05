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

fun multiply(A: Matrix, B: Matrix): Matrix {
    require(A.columns == B.rows) { "A's columns must equal B's rows" }

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

fun transpose(A: Matrix): Matrix {
    val result = Matrix(A.columns, A.rows)
    for (i in 0 until A.rows) {
        for (j in 0 until A.columns) {
            result[j, i] = A[i, j]
        }
    }
    return result
}

fun determinant(A: Matrix): Float {
    require(A.rows == A.columns) { "Determinant is defined only for square matrices" }

    // Base cases
    if (A.rows == 1) return A[0, 0]
    if (A.rows == 2) return A[0, 0] * A[1, 1] - A[0, 1] * A[1, 0]

    var det = 0f
    for (j in 0 until A.columns) {
        det += ((if (j % 2 == 0) 1 else -1) * A[0, j] * determinant(minor(A, 0, j)))
    }
    return det
}

fun minor(A: Matrix, row: Int, col: Int): Matrix {
    val result = Matrix(A.rows - 1, A.columns - 1)
    var r = 0
    for (i in 0 until A.rows) {
        if (i == row) continue
        var c = 0
        for (j in 0 until A.columns) {
            if (j == col) continue
            result[r, c] = A[i, j]
            c++
        }
        r++
    }
    return result
}

fun inverse(A: Matrix): Matrix {
    require(A.rows == A.columns) { "Inverse is defined only for square matrices" }
    val det = determinant(A)
    require(det != 0f) { "Matrix is singular and cannot be inverted" }

    val n = A.rows
    val adjoint = Matrix(n, n)

    for (i in 0 until n) {
        for (j in 0 until n) {
            val sign = if ((i + j) % 2 == 0) 1 else -1
            adjoint[j, i] = (sign * determinant(minor(A, i, j))) / det
        }
    }
    return adjoint
}

fun scaleByFactor(M: Matrix, factor: Float): Matrix {
    val result = Matrix(M.rows, M.columns)
    for (i in 0 until M.rows) {
        for (j in 0 until M.columns) {
            result[i, j] = M[i, j] * factor
        }
    }
    return result
}

