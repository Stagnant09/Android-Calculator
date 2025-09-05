package com.example.calculator.ui.screens.main.equations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.screens.main.components.SideMenu
import com.example.calculator.ui.utils.VSpacer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquationsScreen(
    navigateToMain: () -> Unit,
    navigateToUnitConversion: () -> Unit,
    navigateToTriangle: () -> Unit,
    navigateToConstants: () -> Unit,
    navigateToMatrix: () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SideMenu(
        navigateToMain = navigateToMain,
        navigateToUnitConversion = navigateToUnitConversion,
        navigateToTriangle = navigateToTriangle,
        navigateToConstants = navigateToConstants,
        navigateToEquations = {},
        navigateToMatrix = navigateToMatrix,
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
                    title = { Text(text = "Equation Solver") }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
            ){
                var a by remember { mutableStateOf("") }
                var b by remember { mutableStateOf("") }
                var c by remember { mutableStateOf("") }
                var d by remember { mutableStateOf("") }

                var quadraticResult by remember { mutableStateOf("") }
                var cubicResult by remember { mutableStateOf("") }

                VSpacer(16)
                Text("Quadratic Equation (ax² + bx + c = 0)", style = MaterialTheme.typography.titleMedium)
                VSpacer(8)

                OutlinedTextField(
                    value = a,
                    onValueChange = { a = it },
                    label = { Text("a") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = b,
                    onValueChange = { b = it },
                    label = { Text("b") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = c,
                    onValueChange = { c = it },
                    label = { Text("c") },
                    modifier = Modifier.fillMaxWidth()
                )

                VSpacer(8)
                Button(
                    onClick = {
                        val aVal = a.toDoubleOrNull()
                        val bVal = b.toDoubleOrNull()
                        val cVal = c.toDoubleOrNull()

                        if (aVal != null && bVal != null && cVal != null && aVal != 0.0) {
                            val discriminant = bVal * bVal - 4 * aVal * cVal
                            quadraticResult = if (discriminant > 0) {
                                val root1 = (-bVal + kotlin.math.sqrt(discriminant)) / (2 * aVal)
                                val root2 = (-bVal - kotlin.math.sqrt(discriminant)) / (2 * aVal)
                                "Roots: x₁ = %.4f, x₂ = %.4f".format(root1, root2)
                            } else if (discriminant == 0.0) {
                                val root = -bVal / (2 * aVal)
                                "One real root: x = %.4f".format(root)
                            } else {
                                val real = -bVal / (2 * aVal)
                                val imag = kotlin.math.sqrt(-discriminant) / (2 * aVal)
                                "Complex roots: x₁ = %.4f + %.4fi, x₂ = %.4f - %.4fi"
                                    .format(real, imag, real, imag)
                            }
                        } else {
                            quadraticResult = "Invalid input"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Solve Quadratic")
                }

                if (quadraticResult.isNotEmpty()) {
                    VSpacer(8)
                    Text(quadraticResult, style = MaterialTheme.typography.bodyLarge)
                }

                VSpacer(24)
                Text("Cubic Equation (ax³ + bx² + cx + d = 0)", style = MaterialTheme.typography.titleMedium)
                VSpacer(8)

                OutlinedTextField(
                    value = a,
                    onValueChange = { a = it },
                    label = { Text("a") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = b,
                    onValueChange = { b = it },
                    label = { Text("b") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = c,
                    onValueChange = { c = it },
                    label = { Text("c") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = d,
                    onValueChange = { d = it },
                    label = { Text("d") },
                    modifier = Modifier.fillMaxWidth()
                )

                VSpacer(8)
                Button(
                    onClick = {
                        val aVal = a.toDoubleOrNull()
                        val bVal = b.toDoubleOrNull()
                        val cVal = c.toDoubleOrNull()
                        val dVal = d.toDoubleOrNull()

                        if (aVal != null && bVal != null && cVal != null && dVal != null && aVal != 0.0) {
                            // Normalize coefficients
                            val A = bVal / aVal
                            val B = cVal / aVal
                            val C = dVal / aVal

                            // Depressed cubic substitution: x = y - A/3
                            val p = B - A * A / 3.0
                            val q = 2 * A * A * A / 27.0 - A * B / 3.0 + C
                            val discriminant = q * q / 4.0 + p * p * p / 27.0

                            cubicResult = if (discriminant > 0) {
                                val sqrtDisc = kotlin.math.sqrt(discriminant)
                                val u = kotlin.math.cbrt(-q / 2.0 + sqrtDisc)
                                val v = kotlin.math.cbrt(-q / 2.0 - sqrtDisc)
                                val y = u + v
                                val root = y - A / 3.0
                                "One real root: x = %.4f".format(root)
                            } else if (discriminant == 0.0) {
                                val u = kotlin.math.cbrt(-q / 2.0)
                                val y1 = 2 * u
                                val y2 = -u
                                val root1 = y1 - A / 3.0
                                val root2 = y2 - A / 3.0
                                "Multiple roots: x₁ = %.4f, x₂ = %.4f".format(root1, root2)
                            } else {
                                val r = kotlin.math.sqrt(-p * p * p / 27.0)
                                val phi = kotlin.math.acos(-q / (2 * r))
                                val m = 2 * kotlin.math.sqrt(-p / 3.0)

                                val root1 = m * kotlin.math.cos(phi / 3.0) - A / 3.0
                                val root2 = m * kotlin.math.cos((phi + 2 * Math.PI) / 3.0) - A / 3.0
                                val root3 = m * kotlin.math.cos((phi + 4 * Math.PI) / 3.0) - A / 3.0
                                "Three real roots:\n" +
                                        "x₁ = %.4f\nx₂ = %.4f\nx₃ = %.4f"
                                            .format(root1, root2, root3)
                            }
                        } else {
                            cubicResult = "Invalid input"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Solve Cubic")
                }

                if (cubicResult.isNotEmpty()) {
                    VSpacer(8)
                    Text(cubicResult, style = MaterialTheme.typography.bodyLarge)
                }

            }
        }
    }
}