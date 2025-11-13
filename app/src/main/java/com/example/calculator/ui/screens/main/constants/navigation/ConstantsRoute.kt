package com.example.calculator.ui.screens.main.constants.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.navigation.AppRoute
import com.example.calculator.navigation.navigateTo
import com.example.calculator.ui.screens.main.combinatorics.navigation.navigateToCombinatorics
import com.example.calculator.ui.screens.main.constants.ConstantsScreen
import com.example.calculator.ui.screens.main.constants.ConstantsScreenViewModel
import com.example.calculator.ui.screens.main.currency.navigation.navigateToCurrency
import com.example.calculator.ui.screens.main.equations.navigation.navigateToEquations
import com.example.calculator.ui.screens.main.main.navigation.navigateToMain
import com.example.calculator.ui.screens.main.matrix.navigation.navigateToMatrix
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.navigateToTriangle
import com.example.calculator.ui.screens.main.unitConversion.navigation.navigateToUnitConversion
import kotlinx.serialization.Serializable


fun NavController.navigateToConstants(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = AppRoute.ConstantsRoute, navOptions)
}
fun NavGraphBuilder.constantsScreen(
    navController: NavHostController,
) {
    val viewModel = ConstantsScreenViewModel()
    composable<AppRoute.ConstantsRoute> {
        ConstantsScreen(
            viewModel = viewModel,
            onNavigate = { destination -> navController.navigateTo(destination) }
        )
    }
}
