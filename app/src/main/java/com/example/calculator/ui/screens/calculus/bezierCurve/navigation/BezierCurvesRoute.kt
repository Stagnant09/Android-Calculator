package com.example.calculator.ui.screens.calculus.bezierCurve.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.screens.calculus.bezierCurve.BezierCurvesScreen
import com.example.calculator.ui.screens.calculus.bezierCurve.BezierCurvesViewModel
import com.example.calculator.ui.screens.calculus.functionGraph.FunctionGraphScreen
import com.example.calculator.ui.screens.calculus.functionGraph.FunctionGraphViewModel

fun NavGraphBuilder.bezierCurvesScreen(navController: NavController) {
    val viewModel = BezierCurvesViewModel()
    composable<AppRoute.BezierCurvesRoute> {
        BezierCurvesScreen(
            viewModel = viewModel,
            onNavigate = { route -> navController.navigate(route) }
        )
    }
}