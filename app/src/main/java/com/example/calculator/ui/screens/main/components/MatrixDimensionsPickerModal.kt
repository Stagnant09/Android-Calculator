package com.example.calculator.ui.screens.main.components

import androidx.compose.runtime.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/** This is a modal that allows the user to pick the dimensions of a matrix i.e. rows and columns
 * It pops-up shadowing the rest of the screen like a date range picker.
 *
 * @param showDialog Controls the visibility of the dialog.
 * @param onDismissRequest Called when the user tries to dismiss the dialog (e.g., by clicking outside or back button).
 * @param initialRows The initial number of rows to display.
 * @param initialColumns The initial number of columns to display.
 * @param onConfirm Callback with the selected rows and columns when the OK button is pressed.
 */
@Composable
fun MatrixDimensionsPickerModal(
    showDialog: Boolean,
    onDismissRequest: () -> Unit, // Renamed from cancelButtonAction for standard Dialog behavior
    initialRows: Int,
    initialColumns: Int,
    onConfirm: (rows: Int, cols: Int) -> Unit
) {
    if (showDialog) {
        // Internal state for the picker, initialized with current/initial values
        var selectedRows by remember(initialRows) { mutableStateOf(initialRows.toString()) }
        var selectedColumns by remember(initialColumns) { mutableStateOf(initialColumns.toString()) }

        // State for input validation (optional, but good practice)
        var isRowsError by remember { mutableStateOf(false) }
        var isColumnsError by remember { mutableStateOf(false) }

        fun validateAndConfirm() {
            val rowsInt = selectedRows.toIntOrNull()
            val colsInt = selectedColumns.toIntOrNull()

            isRowsError = rowsInt == null || rowsInt <= 0
            isColumnsError = colsInt == null || colsInt <= 0

            if (!isRowsError && !isColumnsError && rowsInt != null && colsInt != null) {
                onConfirm(rowsInt, colsInt)
            }
        }

        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .width(IntrinsicSize.Min), // Make card wrap content width
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Select Matrix Dimensions",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = selectedRows,
                            onValueChange = {
                                selectedRows = it.filter { char -> char.isDigit() }
                                isRowsError = false // Reset error on change
                            },
                            label = { Text("Rows") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            singleLine = true,
                            isError = isRowsError,
                            modifier = Modifier.weight(1f)
                        )
                        Text("x", style = MaterialTheme.typography.titleMedium)
                        OutlinedTextField(
                            value = selectedColumns,
                            onValueChange = {
                                selectedColumns = it.filter { char -> char.isDigit() }
                                isColumnsError = false // Reset error on change
                            },
                            label = { Text("Columns") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true,
                            isError = isColumnsError,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (isRowsError || isColumnsError) {
                        Text(
                            text = "Please enter valid positive numbers.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End, // Align buttons to the end
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = onDismissRequest) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = ::validateAndConfirm) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatrixDimensionsPickerModalPreview() {
    // State to control dialog visibility for the preview
    var showDialog by remember { mutableStateOf(true) } // Start with true to see it
    var dimensions by remember { mutableStateOf(Pair(3, 3)) }

    // A button to re-open the dialog for testing
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Current Dimensions: ${dimensions.first} x ${dimensions.second}")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { showDialog = true }) {
            Text("Pick Dimensions")
        }
    }

    MatrixDimensionsPickerModal(
        showDialog = showDialog,
        onDismissRequest = { showDialog = false }, // Close dialog on dismiss
        initialRows = dimensions.first,
        initialColumns = dimensions.second,
        onConfirm = { rows, cols ->
            dimensions = Pair(rows, cols)
            showDialog = false // Close dialog on confirm
            println("Confirmed: $rows x $cols")
        }
    )
}
