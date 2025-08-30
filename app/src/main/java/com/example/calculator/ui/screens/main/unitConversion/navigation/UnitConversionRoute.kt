package com.example.calculator.ui.screens.main.unitConversion.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.example.calculator.ui.screens.main.main.MainScreen
import com.example.calculator.ui.screens.main.main.MainScreenViewmodel
import com.example.calculator.ui.screens.main.unitConversion.UnitConversionScreen
import com.example.calculator.ui.screens.main.unitConversion.UnitConversionViewModel
import kotlinx.serialization.Serializable
import androidx.navigation.compose.composable
import com.example.calculator.ui.screens.main.main.navigation.navigateToMain

@Serializable
data object UnitConversionRoute
fun NavController.navigateToUnitConversion(navOptions:
                                  NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = UnitConversionRoute, navOptions)
}
fun NavGraphBuilder.unitConversionScreen(
    navController: NavHostController,
) {
    composable<UnitConversionRoute> {
        UnitConversionScreen(
            viewmodel = UnitConversionViewModel(),
            navigateToMain = { navController.navigateToMain() },
        )
    }
}
