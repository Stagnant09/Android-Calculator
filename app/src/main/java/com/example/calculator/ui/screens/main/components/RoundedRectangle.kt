package com.example.calculator.ui.screens.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** A simple rounded rectangle
 * @param height The height of the rectangle in Dp.
 * @param width The width of the rectangle in Dp.
 * @param radius The corner radius in Dp.
 * @param text The text to display in the rectangle.
 * @param fontSize The font size of the text in Sp.
 * @param verticalTextPadding The vertical padding between the text and the rectangle's border in Dp.
 * @param horizontalTextPadding The horizontal padding between the text and the rectangle's border in Dp.
 * @param backgroundColor The background color of the rectangle.
 * @param textColor The color of the text.
 * @param textAlign The horizontal alignment of the text within its bounds.
 */
@Composable
fun RoundedRectangle(
    modifier: Modifier = Modifier, // Allow passing external modifiers
    height: Dp = 50.dp,
    width: Dp = 200.dp,
    radius: Dp = 12.dp,
    text: String,
    fontSize: TextUnit = 20.sp,
    verticalTextPadding: Dp = 8.dp,
    horizontalTextPadding: Dp = 16.dp,
    backgroundColor: Color = Color.Blue,
    textColor: Color = Color.White,
    textAlign: TextAlign = TextAlign.Center // This is for the Text composable's internal horizontal alignment
) {
    Box(
        modifier = modifier // Apply external modifiers first
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(radius)) // Apply clipping for rounded corners
            .background(backgroundColor)
            .padding( // This padding is for the Text element inside the Box
                horizontal = horizontalTextPadding,
                vertical = verticalTextPadding
            ),
        contentAlignment = Alignment.Center // Vertically and horizontally center the Text block by default
        // within the padding area.
        // The Text's own textAlign will handle alignment *within* the Text block.
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = fontSize,
            textAlign = textAlign, // Apply the specified horizontal text alignment
            modifier = Modifier.fillMaxWidth() // Allow text to use available width for alignment
        )
    }
}

// Overload to accept Float values and convert them to Dp/Sp for convenience
// if you have existing Float-based values.
@Composable
fun RoundedRectangle(
    modifier: Modifier = Modifier,
    height: Float,
    width: Float,
    radius: Float,
    text: String,
    fontSize: Float,
    verticalTextPadding: Float,
    horizontalTextPadding: Float,
    backgroundColor: Color,
    textColor: Color,
    textAlign: TextAlign
) {
    RoundedRectangle(
        modifier = modifier,
        height = height.dp,
        width = width.dp,
        radius = radius.dp,
        text = text,
        fontSize = fontSize.sp,
        verticalTextPadding = verticalTextPadding.dp,
        horizontalTextPadding = horizontalTextPadding.dp,
        backgroundColor = backgroundColor,
        textColor = textColor,
        textAlign = textAlign
    )
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun RoundedRectanglePreview() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RoundedRectangle(
            height = 50.dp,
            width = 200.dp,
            radius = 16.dp,
            text = "Hello Center",
            fontSize = 16.sp,
            verticalTextPadding = 8.dp,
            horizontalTextPadding = 12.dp,
            backgroundColor = Color.Blue,
            textColor = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        RoundedRectangle(
            height = 70f, // Using the Float overload
            width = 250f,
            radius = 25f,
            text = "Left Aligned Text Example With More Content",
            fontSize = 14f,
            verticalTextPadding = 10f,
            horizontalTextPadding = 10f,
            backgroundColor = Color.DarkGray,
            textColor = Color.Yellow,
            textAlign = TextAlign.Left
        )
        Spacer(modifier = Modifier.height(10.dp))
        RoundedRectangle(
            height = 50.dp,
            width = 150.dp,
            radius = 8.dp,
            text = "End",
            fontSize = 18.sp,
            verticalTextPadding = 4.dp,
            horizontalTextPadding = 16.dp,
            backgroundColor = Color(0xFF4CAF50), // Green
            textColor = Color.White,
            textAlign = TextAlign.End // Or TextAlign.Right
        )
    }
}
