package com.example.calculator.ui.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.AppTheme

/** A row of buttons for matrix algebra
 * Structure (From left to right):
 * λ1 * A [some operation] λ2 * B =
 * λ1, λ2 are scalars modifiable by the user in a text field
 * the operation is modifiable by the user by tapping the button
 */
@Composable
fun MatrixAlgebraRow(
    lambda1: String,
    lambda2: String,
    operation: String,
    onLambda1Change: (String) -> Unit,
    onLambda2Change: (String) -> Unit,
    onOperationClick: () -> Unit,
    onEqualsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // λ1
        OutlinedTextField(
            value = lambda1,
            onValueChange = onLambda1Change,
            modifier = Modifier
                .width(70.dp)
                .padding(horizontal = 4.dp),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text("λ₁") }
        )

        Text(" × A ", style = MaterialTheme.typography.bodyLarge)

        // Operation button
        OutlinedButton(
            onClick = onOperationClick,
            modifier = Modifier.padding(horizontal = 2.dp)
        ) {
            Text(operation, fontSize = 24.sp)
        }

        // λ2
        OutlinedTextField(
            value = lambda2,
            onValueChange = onLambda2Change,
            modifier = Modifier
                .width(70.dp)
                .padding(horizontal = 4.dp),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text("λ₂") }
        )

        Text(" × B ", style = MaterialTheme.typography.bodyLarge)

        // Equals button
        OutlinedButton(
            onClick = onEqualsClick,
            modifier = Modifier.padding(horizontal = 2.dp)
        ) {
            Text("=", fontSize = 24.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatrixAlgebraRowPreview() {
    AppTheme {
        MatrixAlgebraRow(
            lambda1 = "1",
            lambda2 = "1",
            operation = "+",
            onLambda1Change = {},
            onLambda2Change = {},
            onOperationClick = {},
            onEqualsClick = {}
        )
    }
}

