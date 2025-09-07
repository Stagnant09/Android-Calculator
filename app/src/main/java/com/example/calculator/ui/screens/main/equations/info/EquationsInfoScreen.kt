package com.example.calculator.ui.screens.main.equations.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.utils.VSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquationsInfoScreen(
    goBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
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
                title = { Text(text = "Info : Equations") },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState)
        ) {
            VSpacer(12)
            Row {
                Text("Quadratic Formula", fontWeight = FontWeight.Bold)
            }
            VSpacer(6)
            Row {
                Text("A quadratic equation has the general form:")
            }
            VSpacer(6)
            Row {
                Text("    ax² + bx + c = 0,   with a ≠ 0")
            }
            VSpacer(12)
            Row {
                Text("The solutions are given by the quadratic formula:")
            }
            VSpacer(6)
            Row {
                Text("    x = (-b ± √(b² - 4ac)) / (2a)")
            }
            VSpacer(12)
            Row {
                Text("Here Δ = b² - 4ac is called the discriminant:")
            }
            VSpacer(6)
            Row {
                Text("• If Δ > 0, there are two distinct real solutions")
            }
            Row {
                Text("• If Δ = 0, there is one real (repeated) solution")
            }
            Row {
                Text("• If Δ < 0, the solutions are complex conjugates")
            }
            VSpacer(32)
            Row {
                Text("Cubic Formula", fontWeight = FontWeight.Bold)
            }
            VSpacer(6)
            Row {
                Text("A cubic equation has the general form:")
            }
            VSpacer(6)
            Row {
                Text("    ax³ + bx² + cx + d = 0,   with a ≠ 0")
            }
            VSpacer(12)
            Row {
                Text("We substitute x = t - b/(3a).")
            }
            Row {
                Text("This eliminates the quadratic term.")
            }
            VSpacer(6)
            Row {
                Text("The reduced equation becomes:")
            }
            Row {
                Text("    t³ + pt + q = 0")
            }
            VSpacer(12)
            Row {
                Text("The discriminant is given by:")
            }
            VSpacer(6)
            Row {
                Text("    Δ = (q/2)² + (p/3)³")
            }
            VSpacer(12)
            Row {
                Text("Cardano’s formula for one root is:")
            }
            VSpacer(6)
            Row {
                Text("    t = ∛(-q/2 + √Δ) + ∛(-q/2 - √Δ)")
            }
            VSpacer(12)
            Row {
                Text("The remaining two roots can be obtained using cube")
            }
            Row {
                Text("roots of unity and may be real or complex depending")
            }
            Row {
                Text("on the sign of the discriminant Δ.")
            }
            VSpacer(12)
            Row {
                Text("• If Δ > 0, there is one real root and two complex")
            }
            Row {
                Text("• If Δ = 0, all roots are real, with at least two equal")
            }
            Row {
                Text("• If Δ < 0, all three roots are distinct and real")
            }
            VSpacer(36)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EquationsInfoScreenPreview() {
    EquationsInfoScreen(
        goBack = {}
    )
}