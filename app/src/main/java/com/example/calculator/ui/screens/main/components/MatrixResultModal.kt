package com.example.calculator.ui.screens.main.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.calculator.models.Matrix
import com.example.calculator.models.MatrixOperationType

/** A modal that displays the result of a matrix operation
 *
 */
@Composable
fun MatrixResultModal(
    opertationType: MatrixOperationType,
    matrix: Matrix = Matrix(0, 0),
    value: Float = 0f,
    showDialog: Boolean,
    onDismissRequest: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
        ) {
            Card(
                modifier = Modifier.padding(16.dp)
            ) {
                when (opertationType) {
                    MatrixOperationType.Unary.Determinant -> Text("Determinant: \n $value", modifier = Modifier.padding(16.dp))
                    MatrixOperationType.Unary.Inverse -> Text("Inverse: \n $matrix", modifier = Modifier.padding(16.dp))
                    MatrixOperationType.Unary.Transpose -> Text("Transpose: \n $matrix", modifier = Modifier.padding(16.dp))
                    MatrixOperationType.Binary.AddMultiply -> Text("Result: \n $matrix", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }

}