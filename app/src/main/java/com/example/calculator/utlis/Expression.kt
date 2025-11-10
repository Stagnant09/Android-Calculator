package com.example.calculator.utlis

import com.example.calculator.models.OperationType
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

enum class ExpressionForm {
    CARTESIAN,
    POLAR
}

/** e.g. 2x^2 + 3y + 1 = 0
 * 0 <= x <= 1
 */
class Expression(
    val form: ExpressionForm = ExpressionForm.CARTESIAN,
    val root: Term,
    val limitations: List<String> = emptyList(),
    val isImplicit: Boolean = false
) {
    /** Normalize, simplify, or isolate dependent variable */
    fun normalize(): Expression {
        fun simplify(term: Term): Term = when (term) {
            is Operation -> {
                val simplifiedOperands = term.operands.map { simplify(it) }

                // Handle constant folding
                if (simplifiedOperands.all { it is Symbol && it.type is SymbolType.Constant }) {
                    val values = simplifiedOperands.map { (it as Symbol).value.toDouble() }
                    val result = when (term.type) {
                        OperationType.BinaryOperationType.Addition -> values[0] + values[1]
                        OperationType.BinaryOperationType.Subtraction -> values[0] - values[1]
                        OperationType.BinaryOperationType.Multiplication -> values[0] * values[1]
                        OperationType.BinaryOperationType.Division -> values[0] / values[1]
                        OperationType.BinaryOperationType.Power -> values[0].pow(values[1])
                        else -> return term.copy(operands = simplifiedOperands)
                    }
                    Symbol(result.toString(), SymbolType.Constant())
                } else {
                    term.copy(operands = simplifiedOperands)
                }
            }
            else -> term
        }

        // First simplify the expression
        val simplifiedRoot = simplify(root)

        // Try to isolate y for Cartesian or r for Polar form
        val normalizedRoot = when (form) {
            ExpressionForm.CARTESIAN -> {
                if (simplifiedRoot is Operation && simplifiedRoot.type is OperationType.BinaryOperationType.Subtraction) {
                    // Try to solve for y
                    val left = simplifiedRoot.operands[0]
                    val right = simplifiedRoot.operands[1]

                    when {
                        left.containsDependent("y") -> {
                            // If left side has y, keep it as is
                            simplifiedRoot
                        }
                        right.containsDependent("y") -> {
                            // If right side has y, swap sides
                            Operation(
                                OperationType.BinaryOperationType.Subtraction,
                                listOf(right, left)
                            )
                        }
                        else -> simplifiedRoot // Can't isolate y, return as is
                    }
                } else {
                    simplifiedRoot
                }
            }
            ExpressionForm.POLAR -> {
                // Similar logic for polar form
                simplifiedRoot
            }
        }
        return Expression(form, normalizedRoot, limitations)
    }

    /** Evaluate given variables */
    fun evaluate(variables: Map<String, Double>): Double {
        fun eval(term: Term): Double = when (term) {
            is Symbol -> when (term.type) {
                is SymbolType.Constant -> term.value.toDoubleOrNull() ?: 0.0
                else -> variables[term.value]
                    ?: error("No value provided for symbol '${term.value}'")
            }

            is Operation -> {
                val operandsEval = term.operands.map { eval(it) }
                when (val t = term.type) {
                    is OperationType.UnaryOperationType -> {
                        val x = operandsEval[0]
                        when (t) {
                            OperationType.UnaryOperationType.Sin -> sin(x)
                            OperationType.UnaryOperationType.Cos -> cos(x)
                            OperationType.UnaryOperationType.Tan -> tan(x)
                            OperationType.UnaryOperationType.Sqrt -> sqrt(x)
                            OperationType.UnaryOperationType.Square -> x * x
                            OperationType.UnaryOperationType.Cube -> x * x * x
                            OperationType.UnaryOperationType.AbsoluteValue -> abs(x)
                            is OperationType.UnaryOperationType.UnaryMinus -> -x
                            else -> error("Unsupported unary operation: $t")
                        }
                    }

                    is OperationType.BinaryOperationType -> {
                        val (a, b) = operandsEval
                        when (t) {
                            OperationType.BinaryOperationType.Addition -> a + b
                            OperationType.BinaryOperationType.Subtraction -> a - b
                            OperationType.BinaryOperationType.Multiplication -> a * b
                            OperationType.BinaryOperationType.Division -> a / b
                            OperationType.BinaryOperationType.Power -> a.pow(b)
                            else -> error("Unsupported binary operation: $t")
                        }
                    }

                    else -> error("Unsupported operation type: ${term.type}")
                }
            }

            else -> error("Unsupported term type: ${term::class.simpleName}")
        }

        return eval(root)
    }

    private fun isImplicitEquation(): Boolean {
        val hasX = root.toString().contains("x", ignoreCase = true)
        val hasY = root.toString().contains("y", ignoreCase = true)
        return hasX && hasY
    }

    /** Convert polar to Cartesian coordinates */
    private fun polarToCartesian(r: Double, theta: Double): Pair<Double, Double> {
        return r * cos(theta) to r * sin(theta)
    }

    /** Evaluate implicit equation at point (x, y) */
    fun evaluateImplicit(x: Double, y: Double): Double {
        val vars = when (form) {
            ExpressionForm.CARTESIAN -> mapOf("x" to x, "y" to y)
            ExpressionForm.POLAR -> {
                val r = sqrt(x * x + y * y)
                val theta = atan2(y, x)
                mapOf("r" to r, "θ" to theta, "theta" to theta, "u" to theta)
            }
        }
        return evaluate(vars)
    }

    override fun toString(): String = root.toString()
}

fun Term.containsDependent(name: String): Boolean {
    return when (this) {
        is Symbol -> this.value == name
        is Operation -> this.operands.any { it.containsDependent(name) }
        else -> false
    }
}
