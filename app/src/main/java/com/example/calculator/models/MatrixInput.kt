package com.example.calculator.models

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun MatrixInput(size: Int, values: List<List<Float>>, onValueChange: (Int, Int, Float) -> Unit) {
    Column {
        for (i in 0 until size) {
            Row {
                for (j in 0 until size) {
                    OutlinedTextField(
                        value = values[i][j].toString(),
                        onValueChange = {
                            val num = it.toFloatOrNull() ?: 0f
                            onValueChange(i, j, num)
                        },
                        singleLine = true,
                        modifier = Modifier
                            .width(64.dp)
                            .padding(2.dp),
                        textStyle = LocalTextStyle.current.copy(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
        }
    }
}