package com.example.calculator.utlis

import com.example.calculator.models.OperationType
import kotlin.math.abs
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
    val limitations: List<String> = emptyList()
) {
    /** Normalize, simplify, or isolate dependent variable */
    /** Normalize, simplify, or isolate dependent variable */
    fun normalize(): Expression {
        fun simplify(term: Term): Term = when (term) {
            is Operation -> {
                val simplifiedOperands = term.operands.map { simplify(it) }

                // Basic constant folding for binary arithmetic
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

        val simplifiedRoot = simplify(root)
        return Expression(form, simplifiedRoot, limitations)
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

    override fun toString(): String = root.toString()
}