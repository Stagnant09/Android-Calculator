package com.example.calculator.ui.screens.main.triangleCalculator.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.utils.VSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriangleInfoScreen(
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Triangle (Formulae)")
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = androidx.compose.ui.Modifier.padding(innerPadding).padding(horizontal = 20.dp)) {
            VSpacer(12)
            Row {
                Text("Let a, b, c be the sides of a triangle and A, B, C be the respective (opposite) angles.")
            }
            VSpacer(12)
            Row {
                Text("Triangle Inequality", fontWeight = FontWeight.Bold)
            }
            VSpacer(6)
            Row {
                Text("It is: a < b + c, b < a + c, c < a + b")
            }
            VSpacer(12)
            Row {
                Text("Sum of angles", fontWeight = FontWeight.Bold)
            }
            VSpacer(6)
            Row {
                Text("It is: A + B + C = 180°")
            }
            VSpacer(12)
            Row {
                Text("Law of Cosines", fontWeight = FontWeight.Bold)
            }
            VSpacer(6)
            Row {
                Text("It is: a^2 = b^2 + c^2 - 2bc cos(A)")
            }
            VSpacer(6)
            Row {
                Text("b^2 = a^2 + c^2 - 2ac cos(B)")
            }
            VSpacer(6)
            Row {
                Text("c^2 = a^2 + b^2 - 2ab cos(C)")
            }
            VSpacer(6)
            Row {
                Text("If A = 90°, then a² = b² + c².")
                Text("  (Pythagorean Theorem)", fontWeight = FontWeight.Bold)
            }
            VSpacer(12)
            Row {
                Text("Perimeter and Area", fontWeight = FontWeight.Bold)
            }
            VSpacer(6)
            Row {
                Text("Perimeter = a + b + c")
            }
            VSpacer(6)
            Row {
                Text("Area = √s(s - a)(s - b)(s - c), where s = (a + b + c) / 2")
            }
            VSpacer(6)
            Row {
                Text("(Heron's Formula)", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TriangleInfoScreenPreview() {
    TriangleInfoScreen(
        goBack = {}
    )
}