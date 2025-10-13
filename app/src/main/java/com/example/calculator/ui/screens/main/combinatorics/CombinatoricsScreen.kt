package com.example.calculator.ui.screens.main.combinatorics

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.components.LabeledTextFieldRow
import com.example.calculator.ui.components.SideMenu
import com.example.calculator.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CombinatoricsScreen(
    viewModel: CombinatoricsViewModel,
    onNavigate: (AppRoute) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

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
                    actions = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {

                            }) {
                                Icon(
                                    painter = rememberVectorPainter(Icons.Default.Info),
                                    contentDescription = "Info",
                                )
                            }
                        }
                    },
                    title = { Text(text = "Combinatorics") }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                LabeledTextFieldRow(
                    label = "Size of set (n)",
                    value = state.n,
                    onValueChange = {viewModel.setEvent(CombinatoricsScreenContract.Event.UpdateN(it))},
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                LabeledTextFieldRow(
                    label = "Items picked (k)",
                    value = state.k,
                    onValueChange = {viewModel.setEvent(CombinatoricsScreenContract.Event.UpdateK(it))},
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                LabeledTextFieldRow(
                    label = "Permutations without repetition",
                    value = state.permutationsWithoutRepetition,
                    onValueChange = {},
                    modifier = Modifier.padding(horizontal = 16.dp),
                    enabled = false
                )
                LabeledTextFieldRow(
                    label = "Permutations with repetition",
                    value = state.permutationsWithRepetition,
                    onValueChange = {},
                    modifier = Modifier.padding(horizontal = 16.dp),
                    enabled = false
                )
                LabeledTextFieldRow(
                    label = "Combinations without repetition",
                    value = state.combinationsWithoutRepetition,
                    onValueChange = {},
                    modifier = Modifier.padding(horizontal = 16.dp),
                    enabled = false
                )
                LabeledTextFieldRow(
                    label = "Combinations with repetition",
                    value = state.combinationsWithRepetition,
                    onValueChange = {},
                    modifier = Modifier.padding(horizontal = 16.dp),
                    enabled = false
                )
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun CombinatoricsScreenPreview() {
    val viewModel = CombinatoricsViewModel()
    AppTheme {
        CombinatoricsScreen(
            viewModel = viewModel,
            onNavigate = {}
        )
    }
}