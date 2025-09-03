package com.example.calculator.ui.screens.main.unitConversion.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.ui.screens.main.constants.navigation.navigateToConstants
import com.example.calculator.ui.screens.main.equations.navigation.navigateToEquations
import com.example.calculator.ui.screens.main.main.navigation.navigateToMain
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.navigateToTriangle
import com.example.calculator.ui.screens.main.unitConversion.UnitConversionScreen
import com.example.calculator.ui.screens.main.unitConversion.UnitConversionViewModel
import kotlinx.serialization.Serializable

@Serializable
data object UnitConversionRoute
fun NavController.navigateToUnitConversion(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = UnitConversionRoute, navOptions)
}
fun NavGraphBuilder.unitConversionScreen(
    navController: NavHostController,
) {
    composable<UnitConversionRoute> {
        UnitConversionScreen(
            viewmodel = UnitConversionViewModel(),
            navigateToMain = { navController.navigateToMain() },
            navigateToTriangle = { navController.navigateToTriangle() },
            navigateToConstants = { navController.navigateToConstants() },
            navigateToEquations = { navController.navigateToEquations() },
        )
    }
}
