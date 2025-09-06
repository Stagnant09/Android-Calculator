package com.example.calculator.ui.screens.main.unitConversion

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.models.UnitType
import com.example.calculator.ui.screens.main.components.SideMenu
import com.example.calculator.ui.screens.main.components.UnitConversionBlock
import com.example.calculator.ui.utils.VSpacer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConversionScreen(
    viewmodel: UnitConversionViewModel,
    navigateToMain: () -> Unit,
    navigateToTriangle: () -> Unit,
    navigateToConstants: () -> Unit,
    navigateToEquations: () -> Unit,
    navigateToMatrix: () -> Unit,
) {
    val state = viewmodel.uiState.collectAsStateWithLifecycle().value
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val unitTypes = listOf(
        UnitType.LengthUnitType::class,
        UnitType.AreaUnitType::class,
        UnitType.AngleUnitType::class,
        UnitType.VolumeUnitType::class,
        UnitType.MassUnitType::class,
        UnitType.TemperatureUnitType::class,
        UnitType.TimeUnitType::class,
        UnitType.SpeedUnitType::class,
        UnitType.PressureUnitType::class,
        UnitType.EnergyUnitType::class,
        UnitType.CurrentUnitType::class,
        UnitType.PowerUnitType::class,
        UnitType.StorageUnitType::class,
    )

    SideMenu(
        navigateToMain = navigateToMain,
        navigateToUnitConversion = {},
        navigateToTriangle = navigateToTriangle,
        navigateToConstants = navigateToConstants,
        navigateToEquations = navigateToEquations,
        navigateToMatrix = navigateToMatrix,
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.Menu),
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "Unit Conversion",
                        )
                    },
                )
            },
            modifier = Modifier

                .fillMaxWidth()
        ) { contentPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                contentPadding = PaddingValues(bottom = 64.dp) // replaces VSpacer(64)
            ) {
                items(unitTypes.size) { unitType ->
                    UnitConversionBlock(
                        unitType = unitTypes[unitType],
                        onEventSent = { event -> viewmodel.setEvent(event) },
                        allValues = state.toUnitMap()
                    )
                }
            }
        }

    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun UnitConversionScreenPreview() {
    val viewmodel = UnitConversionViewModel()
    UnitConversionScreen(
        viewmodel,
        navigateToMain = {},
        navigateToTriangle = {},
        navigateToConstants = {},
        navigateToEquations = {},
        navigateToMatrix = {}
    )
}

