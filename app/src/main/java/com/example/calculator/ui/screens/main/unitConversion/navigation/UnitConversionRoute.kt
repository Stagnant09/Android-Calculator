package com.example.calculator.ui.screens.main.unitConversion.navigation

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
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.navigateToTriangle
import com.example.calculator.ui.screens.main.unitConversion.UnitConversionScreen
import com.example.calculator.ui.screens.main.unitConversion.UnitConversionViewModel
import kotlinx.serialization.Serializable

fun NavController.navigateToUnitConversion(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = AppRoute.UnitConversionRoute, navOptions)
}
fun NavGraphBuilder.unitConversionScreen(
    navController: NavHostController,
) {
    composable<AppRoute.UnitConversionRoute> {
        UnitConversionScreen(
            viewmodel = UnitConversionViewModel(),
            onNavigate = { destination -> navController.navigateTo(destination) }
        )
    }
}
