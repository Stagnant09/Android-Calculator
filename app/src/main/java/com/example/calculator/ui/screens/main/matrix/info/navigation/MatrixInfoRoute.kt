package com.example.calculator.ui.screens.main.matrix.info.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.ui.screens.main.matrix.info.MatrixInfoScreen
import kotlinx.serialization.Serializable

@Serializable
data object MatrixInfoRoute // route to Matrix screen

fun NavController.navigateToMatrixInfo(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = MatrixInfoRoute, navOptions)
}
fun NavGraphBuilder.matrixInfoScreen(
    navController: NavHostController,
) {
    composable<MatrixInfoRoute> {
        MatrixInfoScreen(
            goBack = { navController.navigateUp() },
        )
    }
}
