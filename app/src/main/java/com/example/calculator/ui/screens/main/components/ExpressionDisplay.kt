package com.example.calculator.ui.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.models.Operation
import com.example.calculator.models.OperationType
import com.example.calculator.ui.theme.AppTheme

@Composable
fun ExpressionDisplay(
    currentValue: String
){
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            text = currentValue,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpressionDisplayPreview(){
    val operation = Operation(
        operationType = OperationType.BinaryOperationType.Modulo,
        value = 1F
    )

    AppTheme {
        ExpressionDisplay(
            operation.value.toString()
        )
    }
}