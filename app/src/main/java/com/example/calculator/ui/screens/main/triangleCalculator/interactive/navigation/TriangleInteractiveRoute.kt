package com.example.calculator.ui.screens.main.triangleCalculator.interactive.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.screens.main.triangleCalculator.interactive.TriangleInteractive
import com.example.calculator.ui.screens.main.triangleCalculator.interactive.TriangleInteractiveViewModel
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.navigateToTriangle
import kotlinx.serialization.Serializable

fun NavController.navigateToTriangleInteractive(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = AppRoute.TriangleInteractiveRoute, navOptions)
}

fun NavGraphBuilder.triangleInteractiveScreen(
    navController: NavHostController,
) {
    val viewModel = TriangleInteractiveViewModel()
    composable<AppRoute.TriangleInteractiveRoute> {
        TriangleInteractive(
            goBack = { navController.navigateToTriangle() },
            viewModel = viewModel
        )
    }
}