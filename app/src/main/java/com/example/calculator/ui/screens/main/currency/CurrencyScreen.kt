package com.example.calculator.ui.screens.main.currency

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.statics.currencyUnits
import com.example.calculator.ui.screens.main.components.CurrencyField
import com.example.calculator.ui.screens.main.components.SideMenu
import com.example.calculator.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(
    navigateToMain: () -> Unit,
    navigateToUnitConversion: () -> Unit,
    navigateToTriangle: () -> Unit,
    navigateToConstants: () -> Unit,
    navigateToEquations: () -> Unit,
    navigateToMatrix: () -> Unit,
    viewModel: CurrencyScreenViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.setEvent(CurrencyScreenContract.Event.Init)
    }
    
    SideMenu(
        navigateToMain = navigateToMain,
        navigateToUnitConversion = navigateToUnitConversion,
        navigateToTriangle = navigateToTriangle,
        navigateToConstants = navigateToConstants,
        navigateToEquations = navigateToEquations,
        navigateToMatrix = navigateToMatrix,
        navigateToCurrency = {  },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.Menu),
                                contentDescription = "Menu"
                            )
                        }
                    },
                    title = { Text(text = "Currency Exchange") },
                    actions = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {

                            }) {
                                Icon(
                                    painter = rememberVectorPainter(Icons.Default.Refresh),
                                    contentDescription = "Refresh",
                                )
                            }
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) { paddingValues ->
            CurrencyScreenContent(
                modifier = Modifier.padding(paddingValues),
                state = state,
                onTextFieldEdit = { index, input ->
                    viewModel.setEvent(
                        CurrencyScreenContract.Event.OnInputChanged(
                            index = index, input = input
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun CurrencyScreenContent(
    modifier: Modifier = Modifier,
    state: CurrencyScreenContract.State,
    onTextFieldEdit: (Int, String) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        LazyColumn(
            modifier = modifier.width(360.dp)
        ) {
            items (currencyUnits.size) { index ->
                val currencyUnit = currencyUnits[index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CurrencyField(currencyUnit, state.currencyDisplayValues[index], onCurrencyUnitTap = {  }, onTextFieldEdit = { input ->
                        onTextFieldEdit(index, input)
                    })
                }
            }
        }
    }

}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun CurrencyScreenPreview() {
    val viewModel = CurrencyScreenViewModel()
    AppTheme {
        CurrencyScreen(
            navigateToMain = {},
            navigateToUnitConversion = {},
            navigateToTriangle = {},
            navigateToConstants = {},
            navigateToEquations = {},
            navigateToMatrix = {},
            viewModel = viewModel
        )
    }
}