package com.example.calculator.ui.screens.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.AppTheme
import com.example.calculator.ui.theme.CustomColors

@Composable
fun OperationButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = CustomColors.operationButtonMain,
                contentColor = CustomColors.operationButtonText
            ),
            shape = CircleShape,
            modifier = modifier.height(60.dp).width(60.dp).align(Alignment.Center),
            onClick = onClick
        ){}
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun OperationButtonPreview() {
    AppTheme {
        OperationButton(
            onClick = {  },
            content = { Text("1", fontSize = 20.sp) }
        )
    }
}