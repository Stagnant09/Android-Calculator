package com.example.calculator.ui.screens.main.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.AppThemeCustomColors.colors

@Composable
fun ExpressionDisplay(
    currentValue: String,
    modifier: Modifier = Modifier,
    previousValueAndOperationSymbol: String = ""
){
    Column(
        modifier = modifier.background(colors.displayBackground).fillMaxWidth().fillMaxHeight().padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            text = previousValueAndOperationSymbol,
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 24.sp,
            color = colors.displayOperationHeadLabel
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            text = currentValue,
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 50.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpressionDisplayPreview() {
    ExpressionDisplay(
        previousValueAndOperationSymbol = "1 + ",
        currentValue = "1"
    )
}