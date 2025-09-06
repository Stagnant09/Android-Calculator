package com.example.calculator.ui.screens.main.matrix.help

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculator.ui.screens.main.components.ScreenBase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixInfoScreen() {
    val scrollState = rememberScrollState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ScreenBase(
        scrollState = scrollState,
        drawerState = drawerState,
        scope = scope,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowBack),
                            contentDescription = "Back"
                        )
                    }
                },
                title = { Text(text = "Help : Matrix Algebra") },
            )
        },
        title = "Help : Matrix Algebra",
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun MatrixInfoScreenPreview() {
    MatrixInfoScreen()
}