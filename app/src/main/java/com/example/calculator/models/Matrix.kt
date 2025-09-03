package com.example.calculator.models

data class Matrix(
    val rows: Int,
    val columns: Int,
    val elements: MutableList<MutableList<Float>>
) {
    constructor(rows: Int, columns: Int) : this(
        rows,
        columns,
        MutableList(rows) { MutableList(columns) { 0f } }
    )

    operator fun get(i: Int, j: Int): Float = elements[i][j]
    operator fun set(i: Int, j: Int, value: Float) { elements[i][j] = value }

    override fun toString(): String =
        elements.joinToString("\n") { row -> row.joinToString("\t") }
}

