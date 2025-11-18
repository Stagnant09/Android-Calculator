package com.example.calculator.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.screens.calculus.bezierCurve.navigation.bezierCurvesScreen
import com.example.calculator.ui.screens.calculus.functionGraph.navigation.functionGraphScreen
import com.example.calculator.ui.screens.calculus.integral.navigation.integralScreen
import com.example.calculator.ui.screens.latex.latexCharacterMap.navigation.latexCharacterMapScreen
import com.example.calculator.ui.screens.latex.latexParser.navigation.latexParserScreen
import com.example.calculator.ui.screens.main.combinatorics.navigation.combinatoricsScreen
import com.example.calculator.ui.screens.main.constants.navigation.constantsScreen
import com.example.calculator.ui.screens.main.currency.navigation.currencyScreen
import com.example.calculator.ui.screens.main.equations.info.navigation.equationsInfoScreen
import com.example.calculator.ui.screens.main.equations.navigation.equationsScreen
import com.example.calculator.ui.screens.main.graph.navigation.graphScreen
import com.example.calculator.ui.screens.main.main.navigation.mainScreen
import com.example.calculator.ui.screens.main.matrix.help.navigation.matrixHelpScreen
import com.example.calculator.ui.screens.main.matrix.info.navigation.matrixInfoScreen
import com.example.calculator.ui.screens.main.matrix.navigation.matrixScreen
import com.example.calculator.ui.screens.main.triangleCalculator.info.navigation.triangleInfoScreen
import com.example.calculator.ui.screens.main.triangleCalculator.interactive.navigation.triangleInteractiveScreen
import com.example.calculator.ui.screens.main.triangleCalculator.navigation.triangleScreen
import com.example.calculator.ui.screens.main.unitConversion.navigation.unitConversionScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.MainRoute
    ) {
        mainScreen(navController = navController)
        unitConversionScreen(navController = navController)
        triangleScreen(navController = navController)
        constantsScreen(navController = navController)
        triangleInfoScreen(navController = navController)
        equationsScreen(navController = navController)
        matrixScreen(navController = navController)
        matrixHelpScreen(navController = navController)
        matrixInfoScreen(navController = navController)
        equationsInfoScreen(navController = navController)
        triangleInteractiveScreen(navController = navController)
        currencyScreen(navController = navController)
        combinatoricsScreen(navController = navController)
        graphScreen(navController = navController)
        functionGraphScreen(navController = navController)
        bezierCurvesScreen(navController = navController)
        integralScreen(navController = navController)
        latexParserScreen(navController = navController)
        latexCharacterMapScreen(navController = navController)
    }
}