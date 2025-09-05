package com.example.calculator.models

class Matrix(
    val rows: Int,
    val columns: Int,
    internal val elements: MutableList<MutableList<Float>>
) {
    constructor(rows: Int, columns: Int) : this(
        rows,
        columns,
        MutableList(rows) { MutableList(columns) { 0f } }
    )

    operator fun get(i: Int, j: Int): Float = elements[i][j]
    operator fun set(i: Int, j: Int, value: Float) { elements[i][j] = value }

    fun copy(): Matrix {
        val newElements = elements.map { it.toMutableList() }.toMutableList()
        return Matrix(rows, columns, newElements)
    }

    override fun toString(): String =
        elements.joinToString("\n") { row -> row.joinToString("\t") }
}



