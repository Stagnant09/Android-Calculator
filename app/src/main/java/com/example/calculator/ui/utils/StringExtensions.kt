package com.example.calculator.ui.utils

import java.util.Locale

/** This function convert a string to another string by replacing all '_' with ' ',
 * capitalizing the first letter and lowercasing the rest of the string.
 * E.g. 'hello_world' -> 'Hello World'
 */
fun String.toTitleCase(): String {
    return lowercase(Locale.ROOT).replaceFirstChar { it.uppercase() }.replace("_", " ")
}