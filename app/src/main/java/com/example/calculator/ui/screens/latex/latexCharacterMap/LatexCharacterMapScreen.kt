package com.example.calculator.ui.screens.latex.latexCharacterMap

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.navigation.AppRoute
import com.example.calculator.statics.latexCharactersAndCommands
import com.example.calculator.ui.components.Grid
import com.example.calculator.ui.components.RoundedRectangle
import com.example.calculator.ui.components.SideMenu
import com.example.calculator.ui.theme.AppTheme
import com.example.calculator.ui.utils.HSpacer
import com.example.calculator.ui.utils.VSpacer
import kotlinx.coroutines.launch
import com.example.calculator.ui.theme.AppThemeCustomColors.colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LatexCharacterMapScreen(
    viewModel: LatexCharacterMapViewModel,
    onNavigate: (AppRoute) -> Unit
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SideMenu(
        onNavigate = onNavigate,
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
                    title = { Text(text = "Latex Character Map") },
                    actions = {

                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val scrollState = rememberScrollState()
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = "\\alpha",
                        trailingIcon = {
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    painter = rememberVectorPainter(Icons.Default.ContentCopy),
                                    contentDescription = "Copy"
                                )
                            }
                        },
                        onValueChange = {}
                    )
                }
                VSpacer(8)
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxWidth(0.9f)
                ) {
                    Grid(
                        rows = 30,
                        columns = 6,
                        content =
                            latexCharactersAndCommands.map {
                                {
                                    RoundedRectangle(
                                        text = it.first,
                                        height = 60.dp,
                                        width = 50.dp,
                                        verticalTextPadding = 0.dp,
                                        horizontalTextPadding = 0.dp,
                                        fontSize = 24.sp,
                                        backgroundColor = colors.latexCharacterBackground
                                    )
                                }
                            }
                    )
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LatexCharacterMapScreenPreview() {
    AppTheme {
        LatexCharacterMapScreen(
            viewModel = LatexCharacterMapViewModel(),
            onNavigate = {}
        )
    }
}

