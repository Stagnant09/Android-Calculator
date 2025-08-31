package com.example.calculator.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.calculator.ui.screens.main.main.navigation.MainRoute
import com.example.calculator.ui.screens.main.main.navigation.mainScreen
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.triangleScreen
import com.example.calculator.ui.screens.main.unitConversion.navigation.unitConversionScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute
    ) {
        mainScreen(navController = navController)
        unitConversionScreen(navController = navController)
        triangleScreen(navController = navController)
    }
}