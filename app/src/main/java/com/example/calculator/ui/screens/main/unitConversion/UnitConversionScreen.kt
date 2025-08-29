package com.example.calculator.ui.screens.main.unitConversion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.models.UnitType
import com.example.calculator.ui.screens.main.components.UnitConversionBlock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConversionScreen(
    viewmodel: UnitConversionViewModel
) {
    val state = viewmodel.uiState.collectAsStateWithLifecycle().value

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
                            onClick = { }
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
                    allValues = remember(state) { mutableStateOf(state.toUnitMap()) }.value
                )
                UnitConversionBlock(
                    unitType = UnitType.AngleUnitType::class,
                    onEventSent = { event -> viewmodel.setEvent(event) },
                    allValues = remember(state) { mutableStateOf(state.toUnitMap()) }.value
                )
                UnitConversionBlock(
                    unitType = UnitType.VolumeUnitType::class,
                    onEventSent = { event -> viewmodel.setEvent(event) },
                    allValues = remember(state) { mutableStateOf(state.toUnitMap()) }.value
                )
                UnitConversionBlock(
                    unitType = UnitType.MassUnitType::class,
                    onEventSent = { event -> viewmodel.setEvent(event) },
                    allValues = remember(state) { mutableStateOf(state.toUnitMap()) }.value
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConversionScreenPreview() {
    UnitConversionScreen(UnitConversionViewModel())
}

