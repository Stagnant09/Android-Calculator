package com.example.calculator.utlis

object ExpressionParser {
    fun parse(input: String): Triple<ExpressionForm, List<Term>, List<String>> {
        return Triple(ExpressionForm.CARTESIAN, emptyList(), emptyList())
    }

    /** Returns the value of the dependent variable for the given independent variables of
     * a given expression.
     * e.g. for the expression 2x^2 + 3y + 1 = 0, if x = 1, then the dependent variable is y = -1
     */
    fun evaluate(expression: Expression, independentVariables: Map<Symbol, Double>): Double {
        return 0.0
    }
}