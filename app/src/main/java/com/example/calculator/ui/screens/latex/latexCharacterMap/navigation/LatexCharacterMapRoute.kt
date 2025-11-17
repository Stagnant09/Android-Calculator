package com.example.calculator.ui.screens.latex.latexCharacterMap.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.calculator.interactors.LatexInteractor
import com.example.calculator.navigation.AppRoute
import com.example.calculator.repository.LatexRepository
import com.example.calculator.ui.screens.latex.latexCharacterMap.LatexCharacterMapScreen
import com.example.calculator.ui.screens.latex.latexCharacterMap.LatexCharacterMapViewModel

fun NavGraphBuilder.latexCharacterMapScreen(navController: NavController) {
    val viewModel = LatexCharacterMapViewModel()
    composable<AppRoute.LatexCharacterMapRoute> {
        LatexCharacterMapScreen(
            viewModel = viewModel,
            onNavigate = { route -> navController.navigate(route) }
        )
    }
}