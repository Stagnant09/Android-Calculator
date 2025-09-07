package com.example.calculator.ui.screens.main.matrix.help.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.ui.screens.main.matrix.help.MatrixHelpScreen
import kotlinx.serialization.Serializable

@Serializable
data object MatrixHelpRoute // route to Matrix screen

fun NavController.navigateToMatrixHelp(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = MatrixHelpRoute, navOptions)
}
fun NavGraphBuilder.matrixHelpScreen(
    navController: NavHostController,
) {
    composable<MatrixHelpRoute> {
        MatrixHelpScreen(
            goBack = { navController.navigateUp() },
        )
    }
}
