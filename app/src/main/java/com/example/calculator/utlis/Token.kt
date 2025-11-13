package com.example.calculator.utlis

sealed class Token {
    data class Number(val value: Double) : Token()
    data class Identifier(val name: String) : Token() // x, y, sin, cos, etc.
    data class Operator(val op: String) : Token()     // + - * / ^ etc.
    data object LParen : Token()
    data object RParen : Token()
}

object Tokenizer {
    private val operatorChars = setOf('+', '-', '*', '/', '^', '=', '%')
    private val singleCharOps = operatorChars.map { it.toString() }

    fun tokenize(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var i = 0
        while (i < input.length) {
            when (val c = input[i]) {
                ' ', '\t' -> i++ // skip spaces

                in '0'..'9', '.' -> {
                    val start = i
                    while (i < input.length && (input[i].isDigit() || input[i] == '.')) i++
                    tokens += Token.Number(input.substring(start, i).toDouble())
                }

                in 'a'..'z', in 'A'..'Z' -> {
                    val start = i
                    while (i < input.length && input[i].isLetter()) i++
                    tokens += Token.Identifier(input.substring(start, i))
                }

                '(' -> { tokens += Token.LParen; i++ }
                ')' -> { tokens += Token.RParen; i++ }

                in operatorChars -> {
                    val op = c.toString()
                    if (op in singleCharOps) tokens += Token.Operator(op)
                    i++
                }

                else -> error("Unexpected character: $c")
            }
        }
        return tokens
    }
}


