package com.example.calculator.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.utils.VSpacer

@Composable
fun SideMenu(
    onNavigate: (AppRoute) -> Unit,
    drawerState: DrawerState,
    content: @Composable () -> Unit,
) {
    val menuItemsMain = listOf(
        AppRoute.MainRoute,
        AppRoute.UnitConversionRoute,
        AppRoute.TriangleRoute,
        AppRoute.ConstantsRoute,
        AppRoute.EquationsRoute,
        AppRoute.MatrixRoute,
        AppRoute.CombinatoricsRoute,
        AppRoute.GraphRoute
    )

    val menuItemsCalculus = listOf(
        AppRoute.FunctionGraphRoute,
        AppRoute.BezierCurvesRoute,
        AppRoute.IntegralRoute
    )

    val menuItemsLatex = listOf(
        AppRoute.LatexParserRoute
    )

    val menuItemsFinance = listOf(
        AppRoute.CurrencyRoute
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.fillMaxSize().padding(start = 24.dp)) {
                    VSpacer(36)
                    Text("MAIN", fontWeight = FontWeight.Bold)
                    menuItemsMain.forEach { destination ->
                        NavigationDrawerItem(
                            label = { Text(destination.label) },
                            selected = false,
                            onClick = { onNavigate(destination) }
                        )
                    }
                    Text("CALCULUS", fontWeight = FontWeight.Bold)
                    menuItemsCalculus.forEach { destination ->
                        NavigationDrawerItem(
                            label = { Text(destination.label) },
                            selected = false,
                            onClick = { onNavigate(destination) }
                        )
                    }
                    Text("LATEX", fontWeight = FontWeight.Bold)
                    menuItemsLatex.forEach { destination ->
                        NavigationDrawerItem(
                            label = { Text(destination.label) },
                            selected = false,
                            onClick = { onNavigate(destination) }
                        )
                    }
                    Text("FINANCE", fontWeight = FontWeight.Bold)
                    menuItemsFinance.forEach { destination ->
                        NavigationDrawerItem(
                            label = { Text(destination.label) },
                            selected = false,
                            onClick = { onNavigate(destination) }
                        )
                    }
                }
            }
        }
    ) {
        content()
    }
}