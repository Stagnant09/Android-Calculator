package com.example.calculator.utlis

import com.example.calculator.models.OperationType

object ExpressionParser {
    private var tokens: List<Token> = emptyList()
    private var pos = 0

    // --------------------------
    // Public entry point
    // --------------------------
    fun parse(input: String): Expression {
        val parts = input.split(";").map { it.trim() }
        val equationPart = parts.first()
        val limits = if (parts.size > 1) parts.drop(1) else emptyList()

        val form = if (equationPart.contains('r', ignoreCase = true))
            ExpressionForm.POLAR else ExpressionForm.CARTESIAN

        val (lhs, rhs) = equationPart.split("=").map { it.trim() }

        val leftTerm = parseSubExpression(lhs)
        val rightTerm = parseSubExpression(rhs)

        val root = Operation(
            type = OperationType.BinaryOperationType.Subtraction,
            operands = listOf(leftTerm, rightTerm)
        )

        return Expression(form, root, limits)
    }

    // Parse a single side of an equation
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
                if (peek() is Token.LParen) { // function call, e.g. sin(x)
                    consume()
                    val arg = parseExpression()
                    require(peek() is Token.RParen) { "Missing closing parenthesis after function call" }
                    consume()

                    val opType = when (token.name.lowercase()) {
                        "sin" -> OperationType.UnaryOperationType.Sin
                        "cos" -> OperationType.UnaryOperationType.Cos
                        "tan" -> OperationType.UnaryOperationType.Tan
                        else -> error("Unknown function: ${token.name}")
                    }

                    Operation(opType, listOf(arg))
                } else {
                    Symbol(token.name, SymbolType.IndependentCartesianVariable())
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

}
