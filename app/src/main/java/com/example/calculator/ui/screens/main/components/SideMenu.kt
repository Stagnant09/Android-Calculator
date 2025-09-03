package com.example.calculator.ui.screens.main.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.calculator.ui.utils.VSpacer

@Composable
fun SideMenu(
    navigateToMain: () -> Unit,
    navigateToUnitConversion: () -> Unit,
    navigateToTriangle: () -> Unit,
    navigateToConstants: () -> Unit,
    navigateToEquations: () -> Unit,
    drawerState: DrawerState,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                VSpacer(16)
                NavigationDrawerItem(
                    label = { Text(text = "Calculator") },
                    selected = false,
                    onClick = { navigateToMain() },
                )
                NavigationDrawerItem(
                    label = { Text(text = "Unit Conversion") },
                    selected = false,
                    onClick = { navigateToUnitConversion() }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Triangle") },
                    selected = false,
                    onClick = { navigateToTriangle() }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Constants") },
                    selected = false,
                    onClick = { navigateToConstants() }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Equation Solver") },
                    selected = false,
                    onClick = { navigateToEquations() }
                )
            }
        }
    ) {
        content()
    }
}