package com.example.calculator.ui.screens.main.triangleCalculator.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
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

@Serializable
data object TriangleRoute

fun NavController.navigateToTriangle(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = TriangleRoute, navOptions)
}

fun NavGraphBuilder.triangleScreen(
    navController: NavHostController,
) {
    composable<TriangleRoute> {
        TriangleScreen(
            navigateToMain = { navController.navigateToMain() },
            navigateToUnitConversion = { navController.navigateToUnitConversion() },
            navigateToConstants = { navController.navigateToConstants() },
            navigateToTriangleInfo = { navController.navigateToTriangleInfo() },
            navigateToEquations = { navController.navigateToEquations() },
            navigateToMatrix = { navController.navigateToMatrix() },
            navigateToInteractive = { navController.navigateToTriangleInteractive() },
            navigateToCurrency = {navController.navigateToCurrency()}
        )
    }
}