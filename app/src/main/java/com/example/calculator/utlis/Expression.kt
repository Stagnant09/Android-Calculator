package com.example.calculator.utlis

import com.example.calculator.models.OperationType

enum class ExpressionForm {
    CARTESIAN,
    POLAR
}

/** e.g. 2x^2 + 3y + 1 = 0
 * 0 <= x <= 1
 */
class Expression {
    var form: ExpressionForm = ExpressionForm.CARTESIAN
    var terms: List<Term> = emptyList()
    var limitations: List<String> = emptyList()

    constructor(
        form: ExpressionForm,
        terms: List<Term>,
        limitations: List<String>
    ){
        this.form = form
        this.terms = terms
        this.limitations = limitations
    }

    constructor(
        input: String
    ){
        val (form, terms, limitations) = ExpressionParser.parse(input)
        this.form = form
        this.terms = terms
        this.limitations = limitations
    }

    /** Reorders the terms in the expression in a standard form
     * Cartesian -> y = f(x)
     * Polar -> r = f(θ)
     */
    fun normalise() {
        
    }

}