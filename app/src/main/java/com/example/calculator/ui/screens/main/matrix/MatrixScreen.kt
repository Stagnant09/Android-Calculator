package com.example.calculator.ui.screens.main.matrix

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculator.ui.screens.main.components.ScreenBase
import com.example.calculator.ui.screens.main.components.SideMenu

@Composable
fun MatrixScreen(
    navigateToMain: () -> Unit,
    navigateToUnitConversion: () -> Unit,
    navigateToTriangle: () -> Unit,
    navigateToConstants: () -> Unit,
    navigateToEquations: () -> Unit
) {
    val scrollState = rememberScrollState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SideMenu(
        navigateToMain = navigateToMain,
        navigateToUnitConversion = navigateToUnitConversion,
        navigateToTriangle = navigateToTriangle,
        navigateToConstants = navigateToConstants,
        navigateToEquations = navigateToEquations,
        drawerState = drawerState
    ) {
        ScreenBase(
            scrollState = scrollState,
            drawerState = drawerState,
            scope = scope,
            title = "Matrix",
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatrixScreenPreview() {
    MatrixScreen(
        navigateToMain = { },
        navigateToUnitConversion = { },
        navigateToTriangle = { },
        navigateToConstants = { },
        navigateToEquations = { }
    )
}