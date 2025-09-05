package com.example.calculator.ui.screens.main.matrix.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.ui.screens.main.constants.navigation.navigateToConstants
import com.example.calculator.ui.screens.main.equations.navigation.navigateToEquations
import com.example.calculator.ui.screens.main.main.navigation.navigateToMain
import com.example.calculator.ui.screens.main.matrix.MatrixScreen
import com.example.calculator.ui.screens.main.matrix.MatrixScreenViewModel
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.navigateToTriangle
import com.example.calculator.ui.screens.main.unitConversion.navigation.navigateToUnitConversion
import kotlinx.serialization.Serializable

@Serializable
data object MatrixRoute // route to Matrix screen

fun NavController.navigateToMatrix(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = MatrixRoute, navOptions)
}
fun NavGraphBuilder.matrixScreen(
    navController: NavHostController,
) {
    val viewModel = MatrixScreenViewModel()
    composable<MatrixRoute> {
        MatrixScreen(
            viewModel = viewModel,
            navigateToUnitConversion = { navController.navigateToUnitConversion() },
            navigateToTriangle = { navController.navigateToTriangle() },
            navigateToConstants = { navController.navigateToConstants() },
            navigateToEquations = { navController.navigateToEquations() },
            navigateToMain = { navController.navigateToMain() },
            navigateToMatrix = { },
        )
    }
}
