package com.example.calculator.ui.screens.main.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.ui.screens.main.constants.navigation.navigateToConstants
import com.example.calculator.ui.screens.main.equations.navigation.navigateToEquations
import com.example.calculator.ui.screens.main.main.MainScreen
import com.example.calculator.ui.screens.main.main.MainScreenViewmodel
import com.example.calculator.ui.screens.main.matrix.navigation.navigateToMatrix
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.navigateToTriangle
import com.example.calculator.ui.screens.main.unitConversion.navigation.navigateToUnitConversion
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute // route to User screen

fun NavController.navigateToMain(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = MainRoute, navOptions)
}
fun NavGraphBuilder.mainScreen(
    navController: NavHostController,
) {
    composable<MainRoute> {
        MainScreen(
            viewmodel = MainScreenViewmodel(),
            navigateToUnitConversion = { navController.navigateToUnitConversion() },
            navigateToTriangle = { navController.navigateToTriangle() },
            navigateToConstants = { navController.navigateToConstants() },
            navigateToEquations = { navController.navigateToEquations() },
            navigateToMatrix = { navController.navigateToMatrix() },
            navigateToCurrency = {}
        )
    }
}
