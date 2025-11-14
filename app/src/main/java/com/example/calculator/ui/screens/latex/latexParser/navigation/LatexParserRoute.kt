package com.example.calculator.ui.screens.latex.latexParser.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.screens.latex.latexParser.LatexParserScreen
import com.example.calculator.ui.screens.latex.latexParser.LatexParserViewModel

fun NavGraphBuilder.latexParserScreen(navController: NavController) {
    val viewModel = LatexParserViewModel()
    composable<AppRoute.LatexParserRoute> {
        LatexParserScreen(
            viewModel = viewModel,
            onNavigate = { route -> navController.navigate(route) }
        )
    }
}