package com.example.calculator.ui.components

import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.utils.HSpacer
import com.example.calculator.ui.utils.VSpacer

/**
 * A modal dialog for modifying a numeric value (e.g., edge weight)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModificationDialog(
    initialValue: Float,
    onValueChange: (Float) -> Unit,
    onConfirm: (Float) -> Unit,
    onCancel: () -> Unit,
    label: String = "Value"
) {
    var textValue by remember { mutableStateOf(TextFieldValue(initialValue.toString())) }

    Dialog(onDismissRequest = onCancel) {
        Surface(
            modifier = Modifier.height(160.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                TextField(
                    value = textValue,
                    onValueChange = {
                        // Allow only numeric input
                        if (it.text.matches(Regex("^\\d*\\.?\\d*\$"))) {
                            textValue = it
                            it.text.toFloatOrNull()?.let { f -> onValueChange(f) }
                        }
                    },
                    label = { Text(label) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    trailingIcon = {
                        if (textValue.text.isNotEmpty()) {
                            IconButton(onClick = {
                                textValue = TextFieldValue("")
                                onValueChange(0f)
                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                    },
                    colors = TextFieldDefaults.colors()
                )

                VSpacer(16)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onCancel) {
                        Text("Cancel")
                    }
                    HSpacer(8)
                    Button(onClick = {
                        textValue.text.toFloatOrNull()?.let { value ->
                            onConfirm(value)
                        }
                    }) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModificationDialogPreview() {
    ModificationDialog(
        initialValue = 1.0f,
        onValueChange = {},
        onConfirm = {},
        onCancel = {}
    )
}
