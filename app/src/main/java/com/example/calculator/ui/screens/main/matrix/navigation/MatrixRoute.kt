package com.example.calculator.ui.screens.main.matrix.navigation

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
import com.example.calculator.ui.screens.main.matrix.MatrixScreen
import com.example.calculator.ui.screens.main.matrix.MatrixScreenViewModel
import com.example.calculator.ui.screens.main.matrix.help.navigation.navigateToMatrixHelp
import com.example.calculator.ui.screens.main.matrix.info.navigation.navigateToMatrixInfo
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.navigateToTriangle
import com.example.calculator.ui.screens.main.unitConversion.navigation.navigateToUnitConversion
import kotlinx.serialization.Serializable


fun NavController.navigateToMatrix(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = AppRoute.MatrixRoute, navOptions)
}
fun NavGraphBuilder.matrixScreen(
    navController: NavHostController,
) {
    val viewModel = MatrixScreenViewModel()
    composable<AppRoute.MatrixRoute> {
        MatrixScreen(
            viewModel = viewModel,
            onNavigate = { destination -> navController.navigateTo(destination) }
        )
    }
}
