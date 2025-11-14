package com.example.calculator.repository

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object LatexRepository {

    /** Convert Compose ImageBitmap → Android Bitmap */
    private fun ImageBitmap.toBitmap(): Bitmap = this.asAndroidBitmap()

    /** Save image as PNG and return the file */
    fun saveAsImage(context: Context, image: ImageBitmap): File {
        val bitmap = image.toBitmap()
        val dir = File(context.getExternalFilesDir(null), "latex")
        if (!dir.exists()) dir.mkdirs()

        val file = File(dir, "latex_${System.currentTimeMillis()}.png")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        return file
    }

    /** Save image into a PDF and return the file */
    fun saveAsPdf(context: Context, image: ImageBitmap): File {
        val bitmap = image.toBitmap()

        val pdf = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(
            bitmap.width, bitmap.height, 1
        ).create()

        val page = pdf.startPage(pageInfo)
        page.canvas.drawBitmap(bitmap, 0f, 0f, null)
        pdf.finishPage(page)

        val dir = File(context.getExternalFilesDir(null), "latex")
        if (!dir.exists()) dir.mkdirs()

        val file = File(dir, "latex_${System.currentTimeMillis()}.pdf")
        FileOutputStream(file).use { out ->
            pdf.writeTo(out)
        }
        pdf.close()

        return file
    }

    /** Share image using system share sheet */
    fun share(context: Context, image: ImageBitmap) {
        val file = saveAsImage(context, image)

        // FileProvider URI
        val uri = FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(
            Intent.createChooser(intent, "Share LaTeX Image")
        )
    }
}
