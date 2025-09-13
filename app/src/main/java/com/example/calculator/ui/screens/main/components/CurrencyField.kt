package com.example.calculator.ui.screens.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.models.CurrencyUnit
import com.example.calculator.ui.theme.AppTheme
import com.example.calculator.ui.theme.AppThemeCustomColors.colors
import com.example.calculator.ui.utils.HSpacer

@Composable
fun CurrencyField(
    currencyUnit: CurrencyUnit,
    onCurrencyUnitTap: () -> Unit,
    onTextFieldEdit: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var value by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(56.dp)
    ) {
        Button(
            onClick = onCurrencyUnitTap,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.currencyButtonBackground,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RectangleShape
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = currencyUnit.flag,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp
                    )
                )
                HSpacer(8)
                Text(
                    text = currencyUnit.code,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp
                    )
                )
            }
        }

        TextField(
            value = value,
            onValueChange = {
                value = it
                onTextFieldEdit(it)
            },
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            singleLine = true,
            placeholder = { Text("0.00") },
            shape = RectangleShape,
            textStyle = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp
            ),
            colors = TextFieldDefaults.colors().copy(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyFieldPreview() {
    val usdDollar = CurrencyUnit("United States Dollar", "USD", "$", "🇺🇸")
    AppTheme {
        CurrencyField(currencyUnit = usdDollar, onCurrencyUnitTap = {}, onTextFieldEdit = {})
    }
}
