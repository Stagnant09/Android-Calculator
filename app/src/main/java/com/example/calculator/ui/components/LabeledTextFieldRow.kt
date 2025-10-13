package com.example.calculator.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** This composable is a row that from left to right has
 * 1. a label (Text) -> weight = 0.7
 * 2. a text field   -> weight = 0.3
 */
@Composable
fun LabeledTextFieldRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier
                .weight(0.7f)
                .padding(end = 8.dp),
            fontSize = 16.sp
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(0.3f),
            singleLine = true,
            enabled = enabled
        )
    }
}

@Composable
fun LabeledTextFieldRow(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var textValue by remember(value) { mutableStateOf(value.toString()) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier
                .weight(0.7f)
                .padding(end = 8.dp),
            fontSize = 16.sp
        )

        TextField(
            value = textValue,
            onValueChange = { newText ->
                textValue = newText
                newText.toIntOrNull()?.let { onValueChange(it) }
            },
            modifier = Modifier.weight(0.3f),
            singleLine = true,
            enabled = enabled
        )
    }
}
