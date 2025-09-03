package com.example.calculator.ui.screens.main.triangleCalculator.info.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.ui.screens.main.triangleCalculator.info.TriangleInfoScreen
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.navigateToTriangle
import kotlinx.serialization.Serializable

@Serializable
data object TriangleInfoRoute

fun NavController.navigateToTriangleInfo(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = TriangleInfoRoute, navOptions)
}

fun NavGraphBuilder.triangleInfoScreen(
    navController: NavHostController,
) {
    composable<TriangleInfoRoute> {
        TriangleInfoScreen(
            goBack = { navController.navigateToTriangle() }
        )
    }
}