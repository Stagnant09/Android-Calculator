package com.example.calculator.ui.screens.main.constants.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.ui.screens.main.constants.ConstantsScreen
import com.example.calculator.ui.screens.main.constants.ConstantsScreenViewModel
import com.example.calculator.ui.screens.main.equations.navigation.navigateToEquations
import com.example.calculator.ui.screens.main.main.navigation.navigateToMain
import com.example.calculator.ui.screens.main.matrix.navigation.navigateToMatrix
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.navigateToTriangle
import com.example.calculator.ui.screens.main.unitConversion.navigation.navigateToUnitConversion
import kotlinx.serialization.Serializable

@Serializable
data object ConstantsRoute // route to User screen

fun NavController.navigateToConstants(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = ConstantsRoute, navOptions)
}
fun NavGraphBuilder.constantsScreen(
    navController: NavHostController,
) {
    val viewModel = ConstantsScreenViewModel()
    composable<ConstantsRoute> {
        ConstantsScreen(
            navigateToUnitConversion = { navController.navigateToUnitConversion() },
            navigateToTriangle = { navController.navigateToTriangle() },
            navigateToMain = { navController.navigateToMain() },
            navigateToEquations = { navController.navigateToEquations() },
            navigateToMatrix = { navController.navigateToMatrix() },
            viewModel = viewModel
        )
    }
}
