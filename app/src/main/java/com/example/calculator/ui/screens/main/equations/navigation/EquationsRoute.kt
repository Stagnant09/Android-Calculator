package com.example.calculator.ui.screens.main.equations.navigation

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
import com.example.calculator.ui.screens.main.equations.EquationsScreen
import com.example.calculator.ui.screens.main.equations.info.navigation.navigateToEquationsInfo
import com.example.calculator.ui.screens.main.main.navigation.navigateToMain
import com.example.calculator.ui.screens.main.matrix.navigation.navigateToMatrix
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.navigateToTriangle
import com.example.calculator.ui.screens.main.unitConversion.navigation.navigateToUnitConversion
import kotlinx.serialization.Serializable
fun NavController.navigateToEquations(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = AppRoute.EquationsRoute, navOptions)
}
fun NavGraphBuilder.equationsScreen(
    navController: NavHostController,
) {
    composable<AppRoute.EquationsRoute> {
        EquationsScreen(
            onNavigate = { route -> navController.navigateTo(route) }
        )
    }
}
