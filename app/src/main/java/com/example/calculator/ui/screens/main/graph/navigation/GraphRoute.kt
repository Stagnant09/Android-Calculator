package com.example.calculator.ui.screens.main.graph.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.screens.main.graph.GraphScreen
import com.example.calculator.ui.screens.main.graph.GraphViewModel

fun NavGraphBuilder.graphScreen(navController: NavController) {
    val viewModel = GraphViewModel()
    composable<AppRoute.GraphRoute> {
        GraphScreen(
            viewModel = viewModel,
            onNavigate = { route -> navController.navigate(route) }
        )
    }
}