package com.example.calculator.ui.screens.calculus.integral.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.screens.calculus.integral.IntegralScreen
import com.example.calculator.ui.screens.calculus.integral.IntegralViewModel

fun NavGraphBuilder.integralScreen(navController: NavController) {
    val viewModel = IntegralViewModel()
    composable<AppRoute.IntegralRoute> {
        IntegralScreen(
            viewModel = viewModel,
            onNavigate = { route -> navController.navigate(route) }
        )
    }
}