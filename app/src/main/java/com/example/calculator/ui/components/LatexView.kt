package com.example.calculator.ui.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.rememberWebViewState
import androidx.core.graphics.createBitmap

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LaTeXView(latex: String, saveAsImage: (ImageBitmap) -> Unit) {

    val editedLatex = latex.replace("\\", "\\\\")

    var webView: WebView? by remember { mutableStateOf(null) }

    val state = rememberWebViewState("file:///android_asset/latex_render.html")

    if (state.loadingState is LoadingState.Finished) {
        webView?.loadUrl("javascript:addBody('${editedLatex}')")
    }
    com.google.accompanist.web.WebView(
        state = state,
        modifier = Modifier,
        onCreated = {
            it.settings.javaScriptEnabled = true
            webView = it
            it.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)
            it.setBackgroundColor(0)
        }
    )

    // Convert WebView to ImageBitmap
    val image = webView?.captureAsImageBitmap()
    saveAsImage(image ?: ImageBitmap(0, 0))
}

fun WebView.captureAsImageBitmap(): ImageBitmap? {
    if (this.width <= 0 || this.height <= 0) return null

    // Create a Bitmap with the WebView's current dimensions
    val bitmap = createBitmap(this.width, this.height)
    val canvas = android.graphics.Canvas(bitmap)

    // Draw the WebView's content onto the Canvas
    this.draw(canvas)

    // Convert the Android Bitmap to a Compose ImageBitmap
    return bitmap.asImageBitmap()
}
