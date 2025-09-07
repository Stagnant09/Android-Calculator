package com.example.calculator.ui.screens.main.matrix.help

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculator.ui.screens.main.components.FakeDropdownPreview
import com.example.calculator.ui.screens.main.components.FakeNumberField
import com.example.calculator.ui.screens.main.components.FakeOutlinedButton
import com.example.calculator.ui.screens.main.components.FakePencilIcon
import com.example.calculator.ui.screens.main.components.ScreenBase
import com.example.calculator.ui.utils.HSpacer
import com.example.calculator.ui.utils.VSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixHelpScreen(
    goBack: () -> Unit
) {
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
                        onClick = goBack
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
        VSpacer(32)
        Row {
            Text(
                "Tap the 'Operation' dropdown to select an operation between matrices such as addition/multiplication, inverse matrix calculation, transpose, and determinant.",
                modifier = Modifier.weight(3f)
            )
            Box(modifier = Modifier.weight(2f)) {
                FakeDropdownPreview(label = "Operation")
            }
        }
        VSpacer(24)
        Row {
            Text("Tap any number field to change its value.", modifier = Modifier.weight(6f))
            HSpacer(86)
            Box(modifier = Modifier.weight(2f)) {
                FakeNumberField(value = "0.0")
            }
        }
        VSpacer(24)
        Row {
            Text("Tap the pencil icon to edit the dimensions of the matrix.", modifier = Modifier.weight(8f))
            HSpacer(12)
            Box(modifier = Modifier.weight(2f)) {
                FakePencilIcon()
            }
        }
        VSpacer(24)
        Row {
            Text("Switch between addition and multiplication by tapping the button with the \"+\" or \"*\" symbol.", modifier = Modifier.weight(8f))
            HSpacer(6)
            Box(modifier = Modifier.weight(2f)) {
                FakeOutlinedButton()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MatrixInfoScreenPreview() {
    MatrixHelpScreen(
        goBack = {}
    )
}