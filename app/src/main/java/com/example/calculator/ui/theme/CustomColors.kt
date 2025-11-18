package com.example.calculator.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// 1. Define a CompositionLocal for your custom colors
val LocalCustomColors = staticCompositionLocalOf<CustomColors> {
    error("No CustomColors provided")
}

// 2. Data class for custom colors
data class CustomColors(
    val operationButtonMain: Color,
    val operationButtonText: Color,
    val displayOperationHeadLabel: Color,
    val displayBackground: Color,
    val pencilColor: Color,
    val currencyButtonBackground: Color,
    val nodeGray: Color,
    val nodeOrange: Color,
    val nodeGreen: Color,
    val nodeCyan: Color,
    val nodePurple: Color,
    val latexCharacterBackground: Color
)

// 3. Functions to provide light and dark custom colors
fun lightCustomColors() = CustomColors(
    operationButtonMain = Color(0xFFE6CE7E),
    operationButtonText = Color(0xFF000000),
    displayOperationHeadLabel = Color(0xFF545454),
    displayBackground = Color(0xffe6e6e6),
    pencilColor = Color(0xff191919),
    currencyButtonBackground = Color(0xffdedede),
    Color(220, 220, 220, 255),
    Color(206, 156, 0, 255),
    Color(38, 206, 0, 255),
    Color(0, 189, 206, 255),
    Color(192, 0, 206, 255),
    Color(10, 87, 154, 255)
)

fun darkCustomColors() = CustomColors(
    operationButtonMain = Color(0xff63553d),
    operationButtonText = Color(0xFFFFFFFF),
    displayOperationHeadLabel = Color(0xffdadada),
    displayBackground = Color(0xff0e0e0e),
    pencilColor = Color(0xffededed),
    currencyButtonBackground = Color(0xff353535),
    Color(220, 220, 220, 255),
    Color(206, 156, 0, 255),
    Color(38, 206, 0, 255),
    Color(0, 189, 206, 255),
    Color(192, 0, 206, 255),
    Color(10, 87, 154, 255)
)