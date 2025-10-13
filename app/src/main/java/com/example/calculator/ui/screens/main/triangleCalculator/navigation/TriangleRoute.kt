package com.example.calculator.ui.screens.main.triangleCalculator.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.navigation.AppRoute
import com.example.calculator.navigation.navigateTo
import com.example.calculator.ui.screens.main.combinatorics.navigation.navigateToCombinatorics
import com.example.calculator.ui.screens.main.constants.navigation.navigateToConstants
import com.example.calculator.ui.screens.main.currency.navigation.navigateToCurrency
import com.example.calculator.ui.screens.main.equations.navigation.navigateToEquations
import com.example.calculator.ui.screens.main.main.navigation.navigateToMain
import com.example.calculator.ui.screens.main.matrix.navigation.navigateToMatrix
import com.example.calculator.ui.screens.main.triangleCalculator.TriangleScreen
import com.example.calculator.ui.screens.main.triangleCalculator.info.navigation.navigateToTriangleInfo
import com.example.calculator.ui.screens.main.triangleCalculator.interactive.navigation.navigateToTriangleInteractive
import com.example.calculator.ui.screens.main.unitConversion.navigation.navigateToUnitConversion
import kotlinx.serialization.Serializable

fun NavController.navigateToTriangle(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = AppRoute.TriangleRoute, navOptions)
}

fun NavGraphBuilder.triangleScreen(
    navController: NavHostController,
) {
    composable<AppRoute.TriangleRoute> {
        TriangleScreen(
            onNavigate = { destination -> navController.navigateTo(destination) }
        )
    }
}