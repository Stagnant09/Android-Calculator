package com.example.calculator.native

import com.example.calculator.utlis.ImplicitEvaluator

object NativePlot {
    init {
        System.loadLibrary("implicit_graph")
    }

    external fun computeImplicit(
        width: Int,
        height: Int,
        originX: Float,
        originY: Float,
        step: Float,
        scale: Float,
        threshold: Float,
        formula: String
    ): IntArray
}
