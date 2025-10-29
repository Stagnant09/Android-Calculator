package com.example.calculator.navigation

import kotlinx.serialization.Serializable
// e.g. navigation/AppRoutes.kt
@Serializable
sealed class AppRoute(val includeInSideMenu: Boolean, val label: String) {
    @Serializable data object MainRoute : AppRoute(true, "Calculator")
    @Serializable data object UnitConversionRoute : AppRoute(true, "Unit Conversion")
    @Serializable data object TriangleRoute : AppRoute(true, "Triangle")
    @Serializable data object TriangleInfoRoute : AppRoute(false, "Triangle (Info)")
    @Serializable data object TriangleInteractiveRoute : AppRoute(false, "Triangle (Interactive)")
    @Serializable data object ConstantsRoute : AppRoute(true, "Constants")
    @Serializable data object EquationsRoute : AppRoute(true, "Equation Solver")
    @Serializable data object EquationsInfoRoute : AppRoute(false, "Equation Solver (Info)")
    @Serializable data object MatrixRoute : AppRoute(true, "Matrix Algebra")
    @Serializable data object MatrixHelpRoute : AppRoute(false, "Matrix Algebra (Help)")
    @Serializable data object MatrixInfoRoute : AppRoute(false, "Matrix Algebra (Info)")
    @Serializable data object CurrencyRoute : AppRoute(true, "Currency Exchange")
    @Serializable data object CombinatoricsRoute : AppRoute(true, "Combinatorics")
    @Serializable data object GraphRoute : AppRoute(true, "Graph Calculator")
    @Serializable data object FunctionGraphRoute : AppRoute(true, "Function Graph")
}

