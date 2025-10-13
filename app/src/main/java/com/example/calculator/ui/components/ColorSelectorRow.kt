package com.example.calculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.AppThemeCustomColors.colors
import com.example.calculator.ui.theme.CustomColors
import com.example.calculator.ui.utils.HSpacer

@Composable
fun ColorSelectorRow(
    selectedColorIndex: Int,
    onColorSelected: (Int) -> Unit
) {
    val colors = listOf(
        colors.nodeGray,
        colors.nodeOrange,
        colors.nodeGreen,
        colors.nodeCyan
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Color")

        colors.forEachIndexed { index, color ->
            HSpacer(6)
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color)
                    .clickable { onColorSelected(index) }, // ✅ clickable
                contentAlignment = Alignment.Center
            ) {
                if (selectedColorIndex == index) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}
