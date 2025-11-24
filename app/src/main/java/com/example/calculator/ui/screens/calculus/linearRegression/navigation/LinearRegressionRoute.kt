package com.example.calculator.ui.screens.calculus.linearRegression.navigation

import LinearRegressionScreen
import LinearRegressionViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.calculator.navigation.AppRoute

fun NavGraphBuilder.linearRegressionScreen(navController: NavController) {
    val viewModel = LinearRegressionViewModel()
    composable<AppRoute.LinearRegressionRoute> {
        LinearRegressionScreen(
            viewModel = viewModel,
            onNavigate = { route -> navController.navigate(route) }
        )
    }
}