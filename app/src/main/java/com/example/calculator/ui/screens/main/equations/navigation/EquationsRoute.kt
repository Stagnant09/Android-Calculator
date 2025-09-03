package com.example.calculator.ui.screens.main.equations.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.ui.screens.main.constants.navigation.navigateToConstants
import com.example.calculator.ui.screens.main.equations.EquationsScreen
import com.example.calculator.ui.screens.main.main.navigation.navigateToMain
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.navigateToTriangle
import com.example.calculator.ui.screens.main.unitConversion.navigation.navigateToUnitConversion
import kotlinx.serialization.Serializable

@Serializable
data object EquationsRoute // route to User screen

fun NavController.navigateToEquations(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = EquationsRoute, navOptions)
}
fun NavGraphBuilder.equationsScreen(
    navController: NavHostController,
) {
    composable<EquationsRoute> {
        EquationsScreen(
            navigateToUnitConversion = { navController.navigateToUnitConversion() },
            navigateToTriangle = { navController.navigateToTriangle() },
            navigateToMain = { navController.navigateToMain() },
            navigateToConstants = { navController.navigateToConstants() },
        )
    }
}
