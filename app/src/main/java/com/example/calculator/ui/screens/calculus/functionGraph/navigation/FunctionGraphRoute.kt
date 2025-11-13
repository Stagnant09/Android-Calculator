package com.example.calculator.ui.screens.calculus.functionGraph.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.screens.calculus.functionGraph.FunctionGraphScreen
import com.example.calculator.ui.screens.calculus.functionGraph.FunctionGraphViewModel

fun NavGraphBuilder.functionGraphScreen(navController: NavController) {
    val viewModel = FunctionGraphViewModel()
    composable<AppRoute.FunctionGraphRoute> {
        FunctionGraphScreen(
            viewModel = viewModel,
            onNavigate = { route -> navController.navigate(route) }
        )
    }
}