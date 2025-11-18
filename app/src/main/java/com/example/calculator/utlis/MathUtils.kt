package com.example.calculator.utlis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntSize
import com.example.calculator.models.AngleMode
import com.example.calculator.models.Matrix
import com.example.calculator.models.OperationType
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.acos
import kotlin.math.pow

fun symbolOf(operationType: OperationType): String = when (operationType) {
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

fun toCanvas(x: Double, y: Double, size: Size, step: Float): Offset {
    // origin at canvas center, Y axis up
    val cx = size.width / 2f
    val cy = size.height / 2f
    return Offset(
        (cx + x * step).toFloat(),
        (cy - y * step).toFloat()
    )
}

fun toModel(offset: Offset, size: IntSize, step: Float): Pair<Double, Double> {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val modelX = (offset.x - cx) / step
    val modelY = (cy - offset.y) / step
    return modelX.toDouble() to modelY.toDouble()
}

fun bezierCurve(start: Pair<Float, Float>, end: Pair<Float, Float>, controlPoints: List<Pair<Float, Float>>): List<Offset> {
    val result = mutableListOf<Offset>()
    var t: Double = 0.0
    while (t <= 1) {
        val x = start.first * (1 - t) * (1 - t) * (1 - t) + 3 * controlPoints[0].first * (1 - t) * (1 - t) * t + 3 * controlPoints[1].first * (1 - t) * t * t + end.first * t * t * t
        val y = start.second * (1 - t) * (1 - t) * (1 - t) + 3 * controlPoints[0].second * (1 - t) * (1 - t) * t + 3 * controlPoints[1].second * (1 - t) * t * t + end.second * t * t * t
        result.add(Offset(x.toFloat(), y.toFloat()))
        t += 0.01
    }
    return result
}

/**
 * Returns the parametric formula of a Bézier curve r(t) = (x(t), y(t))
 * for t in [0, 1].
 */
/**
 * Returns the simplified parametric formula of a Bézier curve:
 * r(t) = (x(t), y(t)), for t ∈ [0, 1].
 */
fun bezierCurveParametricFormula(
    start: Pair<Float, Float>,
    end: Pair<Float, Float>,
    controlPoints: List<Pair<Float, Float>>
): Pair<String, String> {
    val points = listOf(start) + controlPoints + listOf(end)
    val n = points.size - 1

    fun binomial(n: Int, k: Int): Int {
        var res = 1
        for (i in 1..k) res = res * (n - i + 1) / i
        return res
    }

    fun buildTerm(coeff: Int, powerOneMinusT: Int, powerT: Int, value: Float): String {
        if (value == 0f) return "" // skip zero terms
        val sb = StringBuilder()

        // coefficient (skip if 1)
        if (coeff != 1 || powerOneMinusT == 0 && powerT == 0) sb.append(coeff)

        // (1 - t)^n
        if (powerOneMinusT > 0) {
            if (sb.isNotEmpty()) sb.append(" * ")
            sb.append("(1 - t)")
            if (powerOneMinusT > 1) sb.append("^$powerOneMinusT")
        }

        // t^n
        if (powerT > 0) {
            if (sb.isNotEmpty()) sb.append(" * ")
            sb.append("t")
            if (powerT > 1) sb.append("^$powerT")
        }

        // multiply by coordinate value
        if (value != 1f) {
            if (sb.isNotEmpty()) sb.append(" * ")
            sb.append(value)
        }

        return sb.toString()
    }

    fun buildFormula(isX: Boolean): String {
        val terms = (0..n).mapNotNull { i ->
            val coeff = binomial(n, i)
            val (x, y) = points[i]
            val value = if (isX) x else y
            buildTerm(coeff, n - i, i, value).takeIf { it.isNotEmpty() }
        }
        return terms.joinToString(" + ")
    }

    val xFormula = "x(t) = ${buildFormula(true)}"
    val yFormula = "y(t) = ${buildFormula(false)}"

    return xFormula to yFormula
}


/**
 * Converts a Cartesian coordinate (x, y) to the corresponding Compose canvas coordinate (Offset).
 *
 * It assumes the following variables are available in the current scope:
 * @param minX The minimum x-value in the data set.
 * @param rangeX The total span of the x-data (maxX - minX).
 * @param width The usable horizontal drawing space (excluding padding).
 * @param minY The minimum y-value in the data set.
 * @param rangeY The total span of the y-data (maxY - minY).
 * @param height The usable vertical drawing space (excluding padding).
 * @param padding The padding on all sides of the plotting area.
 */
fun scalePoint(
    x: Float,
    y: Float,
    minX: Float,
    rangeX: Float,
    width: Float,
    minY: Float,
    rangeY: Float,
    height: Float,
    padding: Float
): Offset {
    // X-axis scaling is correct: increases from left (padding) to right (padding + width).
    val scaledX = padding + ((x - minX) / rangeX) * width

    // Y-axis correction:
    // 1. Calculate the scaled position (0 to height)
    //    This still gives 0 for minY and 'height' for maxY.
    val scaledYFromMin = ((y - minY) / rangeY) * height

    // 2. Invert the Y value relative to the plotting area height.
    //    Since canvas Y increases downwards, (0, -1) should map to a high canvas Y value.
    //    We subtract the scaled value from the total height, then add the top padding.
    //    This correctly maps minY (lowest Cartesian) to padding + height (highest canvas Y)
    //    and maxY (highest Cartesian) to padding (lowest canvas Y).
    val invertedScaledY = padding + (height - scaledYFromMin)

    return Offset(scaledX, invertedScaledY)
}

/** This function converts a string of the form f(x)
 * to its respective lambda function
 */
fun expressionToLambda(expr: String): (Float) -> Float {
    return { x ->
        ExpressionBuilder(expr)
            .variable("x")
            .build()
            .setVariable("x", x.toDouble())
            .evaluate()
            .toFloat()
    }
}

/**
 * Numerically integrate `function` from `lowerLimit` to `upperLimit`.
 * Uses adaptive Simpson's rule (recursive). Returns Float (or Float.NaN on error).
 *
 * Note: Uses Double internally for accuracy, then casts to Float.
 */
fun calculateIntegral(
    function: (Float) -> Float,
    lowerLimit: Float,
    upperLimit: Float,
    eps: Double = 1e-4,         // requested absolute error
    maxRecursionDepth: Int = 20 // prevents runaway recursion
): Float {
    // quick checks
    if (lowerLimit.isNaN() || upperLimit.isNaN()) return Float.NaN
    if (lowerLimit == upperLimit) return 0f

    // allow reversed limits
    val sign = if (lowerLimit <= upperLimit) 1.0 else -1.0
    val a = minOf(lowerLimit, upperLimit).toDouble()
    val b = maxOf(lowerLimit, upperLimit).toDouble()

    // wrapper to call the user's function with double precision
    fun f(x: Double): Double {
        val fx = function(x.toFloat())
        return fx.toDouble()
    }

    // Simpson estimate on [a, b]
    fun simpson(a: Double, b: Double, fa: Double, fb: Double, fm: Double): Double {
        return (fa + 4.0 * fm + fb) * (b - a) / 6.0
    }

    // Adaptive Simpson recursion
    fun adaptiveSimpson(
        a: Double,
        b: Double,
        fa: Double,
        fb: Double,
        fm: Double,
        whole: Double,
        eps: Double,
        depth: Int
    ): Double {
        val m = (a + b) / 2.0
        val lm = (a + m) / 2.0
        val rm = (m + b) / 2.0

        val flm = try { f(lm) } catch (e: Throwable) { Double.NaN }
        val frm = try { f(rm) } catch (e: Throwable) { Double.NaN }

        // If function produced NaN or infinite values, bail out
        if (!flm.isFinite() || !frm.isFinite()) return Double.NaN

        val left = simpson(a, m, fa, fm, flm)
        val right = simpson(m, b, fm, fb, frm)
        val delta = left + right - whole

        // If good enough or max depth reached, return corrected estimate
        return if (depth <= 0 || kotlin.math.abs(delta) <= 15.0 * eps) {
            // Richardson extrapolation
            left + right + delta / 15.0
        } else {
            // Recurse on left and right halves with half tolerance
            val leftRes = adaptiveSimpson(a, m, fa, fm, flm, left, eps / 2.0, depth - 1)
            if (!leftRes.isFinite()) return Double.NaN
            val rightRes = adaptiveSimpson(m, b, fm, fb, frm, right, eps / 2.0, depth - 1)
            if (!rightRes.isFinite()) return Double.NaN
            leftRes + rightRes
        }
    }

    // initial function evaluations
    val fa = try { f(a) } catch (e: Throwable) { Double.NaN }
    val fb = try { f(b) } catch (e: Throwable) { Double.NaN }
    val m = (a + b) / 2.0
    val fm = try { f(m) } catch (e: Throwable) { Double.NaN }

    if (!fa.isFinite() || !fb.isFinite() || !fm.isFinite()) return Float.NaN

    val initial = simpson(a, b, fa, fb, fm)
    val result = adaptiveSimpson(a, b, fa, fb, fm, initial, eps, maxRecursionDepth)

    return if (!result.isFinite()) Float.NaN else (sign * result).toFloat()
}

val floatRegex = Regex("^[-]?\\d*\\.?\\d+$")