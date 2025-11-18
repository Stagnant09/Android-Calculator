package com.example.calculator.ui.components

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.createBitmap
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.rememberWebViewState
import kotlinx.coroutines.delay

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LaTeXView(latex: String, saveAsImage: (ImageBitmap) -> Unit) {

    // Escape backslashes for JS injection
    val editedLatex = latex.replace("\\", "\\\\")

    var webView: WebView? by remember { mutableStateOf(null) }

    val state = rememberWebViewState("file:///android_asset/latex_render.html")

    // 1. Execute JS to render LaTeX when the base HTML is finished loading
    if (state.loadingState is LoadingState.Finished) {
        webView?.loadUrl("javascript:addBody('${editedLatex}')")
    }

    // 2. The Accompanist WebView component
    com.google.accompanist.web.WebView(
        state = state,
        modifier = Modifier, // Use Modifier.fillMaxSize() for a better view
        onCreated = {
            it.settings.javaScriptEnabled = true
            webView = it
            it.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)
            it.setBackgroundColor(0) // Transparent background
        }
    )

    // 3. Capture the image after the content is likely rendered
    LaunchedEffect(state.loadingState) {
        if (state.loadingState is LoadingState.Finished) {
            // Wait for the JS injection and subsequent rendering to complete.
            // A hardcoded delay is a hack, but often necessary for web rendering tasks.
            delay(500) // Adjust this delay if the rendering is too slow/fast

            val image = webView?.captureVisibleContentAsImageBitmap()
            if (image != null) {
                // Call your repository function with the captured ImageBitmap
                saveAsImage(image)
            }
        }
    }
}

/**
 * Captures the currently visible content of the WebView as a Compose ImageBitmap.
 */
fun WebView.captureVisibleContentAsImageBitmap(): ImageBitmap? {
    // 1. Check for valid dimensions
    if (this.width <= 0 || this.height <= 0) return null

    // 2. Create a Bitmap with the WebView's current dimensions
    val bitmap = try {
        createBitmap(this.width, this.height)
    } catch (e: Exception) {
        // Handle potential OOM errors for very large views
        e.printStackTrace()
        return null
    }

    // 3. Draw the WebView's content onto the Canvas
    val canvas = Canvas(bitmap)
    this.draw(canvas)

    // 4. Convert the Android Bitmap to a Compose ImageBitmap
    return bitmap.asImageBitmap()
}
