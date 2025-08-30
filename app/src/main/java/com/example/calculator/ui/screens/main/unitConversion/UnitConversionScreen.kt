package com.example.calculator.ui.screens.main.unitConversion

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.models.UnitType
import com.example.calculator.ui.screens.main.components.SideMenu
import com.example.calculator.ui.screens.main.components.UnitConversionBlock
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConversionScreen(
    viewmodel: UnitConversionViewModel,
    navigateToMain: () -> Unit
) {
    val state = viewmodel.uiState.collectAsStateWithLifecycle().value

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SideMenu(
        navigateToMain = navigateToMain,
        navigateToUnitConversion = {},
        drawerState = drawerState
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Spacer(
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxWidth()
                    .background(Color.Black)
            )
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
                    .weight(1F)
                    .fillMaxWidth()
            ) { contentPadding ->
                Column(
                    modifier = Modifier.padding(contentPadding)
                ) {
                    UnitConversionBlock(
                        unitType = UnitType.LengthUnitType::class,
                        onEventSent = { event ->
                            viewmodel.setEvent(event)
                        },
                        allValues = state.toUnitMap()
                    )
                    UnitConversionBlock(
                        unitType = UnitType.AreaUnitType::class,
                        onEventSent = { event -> viewmodel.setEvent(event) },
                        allValues = state.toUnitMap()
                    )
                    UnitConversionBlock(
                        unitType = UnitType.AngleUnitType::class,
                        onEventSent = { event -> viewmodel.setEvent(event) },
                        allValues = state.toUnitMap()
                    )
                    UnitConversionBlock(
                        unitType = UnitType.VolumeUnitType::class,
                        onEventSent = { event -> viewmodel.setEvent(event) },
                        allValues = state.toUnitMap()
                    )
                    UnitConversionBlock(
                        unitType = UnitType.MassUnitType::class,
                        onEventSent = { event -> viewmodel.setEvent(event) },
                        allValues = state.toUnitMap()
                    )
                    UnitConversionBlock(
                        unitType = UnitType.TemperatureUnitType::class,
                        onEventSent = { event -> viewmodel.setEvent(event) },
                        allValues = state.toUnitMap()
                    )
                    UnitConversionBlock(
                        unitType = UnitType.TimeUnitType::class,
                        onEventSent = { event -> viewmodel.setEvent(event) },
                        allValues = state.toUnitMap()
                    )
                    UnitConversionBlock(
                        unitType = UnitType.SpeedUnitType::class,
                        onEventSent = { event -> viewmodel.setEvent(event) },
                        allValues = state.toUnitMap()
                    )
                    UnitConversionBlock(
                        unitType = UnitType.PressureUnitType::class,
                        onEventSent = { event -> viewmodel.setEvent(event) },
                        allValues = state.toUnitMap()
                    )
                    UnitConversionBlock(
                        unitType = UnitType.EnergyUnitType::class,
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
        navigateToMain = {})
}

