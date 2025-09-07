package com.example.calculator.ui.screens.main.equations.info.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calculator.ui.screens.main.equations.info.EquationsInfoScreen
import kotlinx.serialization.Serializable

@Serializable
data object EquationsInfoRoute // route to Equations info screen

fun NavController.navigateToEquationsInfo(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = EquationsInfoRoute, navOptions)
}
fun NavGraphBuilder.equationsInfoScreen(
    navController: NavHostController,
) {
    composable<EquationsInfoRoute> {
        EquationsInfoScreen(
            goBack = { navController.navigateUp() },
        )
    }
}
