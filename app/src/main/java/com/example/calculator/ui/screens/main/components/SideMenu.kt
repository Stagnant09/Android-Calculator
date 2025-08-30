package com.example.calculator.ui.screens.main.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SideMenu(
    navigateToMain: () -> Unit,
    navigateToUnitConversion: () -> Unit,
    drawerState: DrawerState,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
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
            }
        }
    ) {
        content()
    }
}