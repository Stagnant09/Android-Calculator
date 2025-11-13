package com.example.calculator.ui.screens.main.combinatorics.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.navigation.AppRoute
import com.example.calculator.navigation.navigateTo
import com.example.calculator.ui.screens.main.combinatorics.CombinatoricsScreen
import com.example.calculator.ui.screens.main.combinatorics.CombinatoricsViewModel
import com.example.calculator.ui.screens.main.constants.navigation.navigateToConstants
import com.example.calculator.ui.screens.main.currency.navigation.navigateToCurrency
import com.example.calculator.ui.screens.main.equations.navigation.navigateToEquations
import com.example.calculator.ui.screens.main.main.navigation.navigateToMain
import com.example.calculator.ui.screens.main.matrix.navigation.navigateToMatrix
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.navigateToTriangle
import com.example.calculator.ui.screens.main.unitConversion.navigation.navigateToUnitConversion
import kotlinx.serialization.Serializable

fun NavController.navigateToCombinatorics(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = AppRoute.CombinatoricsRoute, navOptions)
}
fun NavGraphBuilder.combinatoricsScreen(
    navController: NavHostController,
) {
    val viewModel = CombinatoricsViewModel()
    composable<AppRoute.CombinatoricsRoute> {
        CombinatoricsScreen(
            viewModel = viewModel,
            onNavigate = { destination -> navController.navigateTo(destination) }
        )
    }
}
