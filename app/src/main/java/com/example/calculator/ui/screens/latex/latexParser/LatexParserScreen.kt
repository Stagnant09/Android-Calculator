package com.example.calculator.ui.screens.latex.latexParser

import android.graphics.Outline
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.components.LaTeXView
import com.example.calculator.ui.components.SideMenu
import com.example.calculator.ui.screens.calculus.integral.IntegralScreenContent
import com.example.calculator.ui.screens.latex.latexParser.LatexParserScreen
import com.example.calculator.ui.utils.HSpacer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LatexParserScreen(
    viewModel: LatexParserViewModel,
    onNavigate: (AppRoute) -> Unit,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
                    title = { Text(text = "Latex Parser") },
                    actions = {

                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.fillMaxWidth(0.85f)){
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth().height(360.dp),
                        value = state.latex,
                        onValueChange = { viewModel.setEvent(LatexParserContract.Event.ParseLatex(it)) },
                        label = { Text("LaTeX") }
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(0.85f)){
                    LaTeXView(latex = state.latex, saveAsImage = {
                        viewModel.setEvent(LatexParserContract.Event.StoreImage(it))
                    })
                }
                Row(modifier = Modifier.fillMaxWidth(0.80f), horizontalArrangement = Arrangement.SpaceEvenly){
                    Button(onClick = {
                        viewModel.setEvent(LatexParserContract.Event.SaveAsImage(context))
                    }) {
                        Text("Save as image")
                    }
                    HSpacer(6)
                    Button(onClick = {
                        viewModel.setEvent(LatexParserContract.Event.SaveAsPdf(context))
                    }) {
                        Text("Save as PDF")
                    }
                    HSpacer(6)
                    IconButton(onClick = {
                        viewModel.setEvent(LatexParserContract.Event.Share(context))
                    }) {
                        Icon(
                            painter = rememberVectorPainter(Icons.Default.Share),
                            contentDescription = "Share"
                        )
                    }
                }
            }
        }
    }
}
