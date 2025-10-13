package com.example.calculator.ui.screens.main.equations.info.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.screens.main.equations.info.EquationsInfoScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToEquationsInfo(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = AppRoute.EquationsInfoRoute, navOptions)
}
fun NavGraphBuilder.equationsInfoScreen(
    navController: NavHostController,
) {
    composable<AppRoute.EquationsInfoRoute> {
        EquationsInfoScreen(
            goBack = { navController.navigateUp() },
        )
    }
}
