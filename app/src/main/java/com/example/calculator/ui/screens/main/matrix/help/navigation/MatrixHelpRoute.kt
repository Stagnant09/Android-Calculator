package com.example.calculator.ui.screens.main.matrix.help.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.screens.main.matrix.help.MatrixHelpScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToMatrixHelp(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = AppRoute.MatrixHelpRoute, navOptions)
}
fun NavGraphBuilder.matrixHelpScreen(
    navController: NavHostController,
) {
    composable<AppRoute.MatrixHelpRoute> {
        MatrixHelpScreen(
            goBack = { navController.navigateUp() },
        )
    }
}
