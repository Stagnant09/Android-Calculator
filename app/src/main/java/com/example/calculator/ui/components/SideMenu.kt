package com.example.calculator.ui.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.calculator.navigation.AppRoute

@Composable
fun SideMenu(
    onNavigate: (AppRoute) -> Unit,
    drawerState: DrawerState,
    content: @Composable () -> Unit,
) {
    val menuItems = listOf(
        AppRoute.MainRoute,
        AppRoute.UnitConversionRoute,
        AppRoute.TriangleRoute,
        AppRoute.ConstantsRoute,
        AppRoute.EquationsRoute,
        AppRoute.MatrixRoute,
        AppRoute.CombinatoricsRoute,
        AppRoute.GraphRoute
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                menuItems.forEach { destination ->
                    NavigationDrawerItem(
                        label = { Text(destination.label) },
                        selected = false,
                        onClick = { onNavigate(destination) }
                    )
                }
            }
        }
    ) {
        content()
    }
}