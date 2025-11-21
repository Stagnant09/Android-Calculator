package com.example.calculator.utlis

import android.util.Log
import com.example.calculator.models.OperationType

object ExpressionParser {
    private var tokens: List<Token> = emptyList()
    private var pos = 0
    private var isPolarContext = false

    fun parse(input: String): Expression? {
        try {
            val parts = input.split(";").map { it.trim() }
            val equationPart = parts.first()
            val limits = if (parts.size > 1) parts.drop(1) else emptyList()

            // Check if equation is polar
            val form = if (isPolarEquation(equationPart)) {
                ExpressionForm.POLAR
            } else {
                ExpressionForm.CARTESIAN
            }

            if (isVerticalLine(equationPart).first) {
                Log.d("ExpressionParser", "Vertical line detected")
                Log.d("ExpressionParser", "Returning: ${isVerticalLine(equationPart)}")
                return Expression(
                    input,
                    form,
                    Operation(
                        OperationType.UnaryOperationType.UnaryMinus,
                        listOf(Symbol("x", SymbolType.IndependentCartesianVariable()))
                    ),
                    limits,
                    false,
                    isVerticalLine(equationPart)
                )
            }

            val sides = equationPart.split("=").map { it.trim() }
            if (sides.size != 2) return null

            val (lhs, rhs) = sides
            isPolarContext = (form == ExpressionForm.POLAR)
            val leftTerm = parseSubExpression(lhs)
            val rightTerm = parseSubExpression(rhs)
            isPolarContext = false

            // Check if equation is implicit
            val isImplicit = isImplicitEquation(equationPart)

            val root = when {
                // Handle polar equations
                form == ExpressionForm.POLAR -> {
                    Operation(
                        OperationType.BinaryOperationType.Subtraction,
                        listOf(leftTerm, rightTerm)
                    )
                }
                // Handle implicit equations
                isImplicit -> {
                    Operation(
                        OperationType.BinaryOperationType.Subtraction,
                        listOf(leftTerm, rightTerm)
                    )
                }
                // Handle explicit y = f(x) or x = f(y)
                else -> {
                    when {
                        leftTerm.containsDependent("y") || rightTerm.containsDependent("x") ->
                            Operation(
                                OperationType.BinaryOperationType.Subtraction,
                                listOf(leftTerm, rightTerm)
                            )

                        else ->
                            Operation(
                                OperationType.BinaryOperationType.Subtraction,
                                listOf(rightTerm, leftTerm)
                            )
                    }
                }
            }

            return Expression(input, form, root, limits, isImplicit, Pair(false,0f))
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun isPolarEquation(equation: String): Boolean {
        val polarVars = listOf("r", "θ", "theta", "u")
        val hasPolarVar = polarVars.any { it in equation.lowercase() }
        val hasCartesianVars = listOf("x", "y").any { it in equation.lowercase() }
        return hasPolarVar && !hasCartesianVars
    }

    private fun isImplicitEquation(equation: String): Boolean {
        // Cases that an equation is implicit:
        // 1. y is raised to a power
        // 2. x is raised to a power and y is not present
        return (equation.contains("y^") || (equation.contains("x^") && !equation.contains("y")) && equation.contains("="))
    }

    private fun parseSubExpression(expr: String): Term {
        tokens = Tokenizer.tokenize(expr)
        pos = 0
        return parseExpression()
    }

    // --------------------------
    // Recursive descent parser
    // --------------------------
    private fun peek(): Token? = tokens.getOrNull(pos)
    private fun consume(): Token = tokens[pos++]

    private fun parseExpression(): Term {
        var left = parseTerm()
        while (peek() is Token.Operator && (peek() as Token.Operator).op in listOf("+", "-")) {
            val op = (consume() as Token.Operator).op
            val right = parseTerm()
            left = Operation(
                type = if (op == "+")
                    OperationType.BinaryOperationType.Addition
                else
                    OperationType.BinaryOperationType.Subtraction,
                operands = listOf(left, right)
            )
        }
        return left
    }

    private fun parseTerm(): Term {
        var left = parseFactor()
        while (peek() is Token.Operator && (peek() as Token.Operator).op in listOf("*", "/")) {
            val op = (consume() as Token.Operator).op
            val right = parseFactor()
            left = Operation(
                type = if (op == "*")
                    OperationType.BinaryOperationType.Multiplication
                else
                    OperationType.BinaryOperationType.Division,
                operands = listOf(left, right)
            )
        }
        return left
    }

    private fun parseFactor(): Term {
        // Handle unary plus/minus
        var unaryCount = 0
        while (peek() is Token.Operator && ((peek() as Token.Operator).op == "+" || (peek() as Token.Operator).op == "-")) {
            val op = (consume() as Token.Operator).op
            if (op == "-") unaryCount++
        }

        var left = parseBase()

        // Apply unary minus the correct number of times
        repeat(unaryCount) {
            left = Operation(
                type = OperationType.UnaryOperationType.UnaryMinus,
                operands = listOf(left)
            )
        }

        // Handle exponentiation
        if (peek() is Token.Operator && (peek() as Token.Operator).op == "^") {
            consume()
            val exponent = parseFactor()
            left = Operation(
                type = OperationType.BinaryOperationType.Power,
                operands = listOf(left, exponent)
            )
        }

        // Handle implicit multiplication (e.g., 3x or 2sin(x))
        while (peek() != null && (peek() is Token.Number || peek() is Token.Identifier || peek() is Token.LParen)) {
            val right = parseFactor() // recursively parse next factor
            left = Operation(
                type = OperationType.BinaryOperationType.Multiplication,
                operands = listOf(left, right)
            )
        }

        return left
    }

    private fun parseBase(): Term {
        return when (val token = peek()) {
            is Token.Number -> {
                consume()
                Symbol(token.value.toString(), SymbolType.Constant())
            }

            is Token.Identifier -> {
                consume()
                if (peek() is Token.LParen) {
                    // Function call
                    consume()
                    val arg = parseExpression()
                    require(peek() is Token.RParen) { "Missing closing parenthesis" }
                    consume()

                    val opType = when (token.name.lowercase()) {
                        "sin" -> OperationType.UnaryOperationType.Sin
                        "cos" -> OperationType.UnaryOperationType.Cos
                        "tan" -> OperationType.UnaryOperationType.Tan
                        "sqrt" -> OperationType.UnaryOperationType.Sqrt
                        "abs" -> OperationType.UnaryOperationType.AbsoluteValue
                        else -> error("Unknown function: ${token.name}")
                    }

                    Operation(opType, listOf(arg))
                } else {
                    // Variable
                    val varName = token.name.lowercase()
                    when {
                        isPolarContext && varName in listOf("r", "theta", "θ", "u") ->
                            Symbol(varName, SymbolType.IndependentPolarVariable())

                        varName == "x" ->
                            Symbol("x", SymbolType.IndependentCartesianVariable())

                        varName == "y" ->
                            Symbol("y", SymbolType.DependentCartesianVariable())

                        else ->
                            Symbol(varName, SymbolType.Constant())
                    }
                }
            }

            is Token.LParen -> {
                consume()
                val expr = parseExpression()
                require(peek() is Token.RParen) { "Missing closing parenthesis" }
                consume()
                expr
            }

            else -> error("Unexpected token: $token")
        }
    }

    private fun isVerticalLine(equation: String): Pair<Boolean, Float> {
        Log.d("ExpressionParser", "isVerticalLine: $equation")
        // Split equation into left and right sides
        val sides = equation.split("=")
        Log.d("ExpressionParser", "sides: ${sides[0]} = ${sides[1]}")
        if (sides.size != 2) return Pair(false, 0f)

        // Check if left side is x
        if (sides[0].trim() != "x") return Pair(false, 0f)

        // Check if right side is a constant
        val rightSide = sides[1].trim()
        Log.d("ExpressionParser", "rightSide: $rightSide")
        if (rightSide.toDoubleOrNull() == null) return Pair(false, 0f)
        Log.d("ExpressionParser", "rightSide.toDoubleOrNull(): ${rightSide.toDoubleOrNull()}")
        Log.d(
            "ExpressionParser",
            "${rightSide.matches(Regex("^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$"))}"
        )
        return Pair(
            rightSide.matches(Regex("^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$")),
            rightSide.toFloat()
        )
    }
}
