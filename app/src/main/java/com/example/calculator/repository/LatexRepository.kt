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
import android.os.Environment
import android.provider.MediaStore
import android.content.ContentValues
import android.net.Uri

object LatexRepository {

    private fun ImageBitmap.toBitmap(): Bitmap = this.asAndroidBitmap()

    // ------------------------------
    //  SAVE PNG TO PICTURES/LaTeX
    // ------------------------------
    fun saveAsImage(context: Context, image: ImageBitmap): File {
        val bitmap = image.toBitmap()
        val filename = "latex_${System.currentTimeMillis()}.png"

        val picturesDir = Environment.DIRECTORY_PICTURES
        val subfolder = "LaTeX"

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, "$picturesDir/$subfolder")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        ) ?: throw Exception("Could not create image file")

        context.contentResolver.openOutputStream(uri).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out!!)
        }

        values.clear()
        values.put(MediaStore.Images.Media.IS_PENDING, 0)
        context.contentResolver.update(uri, values, null, null)

        return File(
            Environment.getExternalStoragePublicDirectory(picturesDir)
                .absolutePath + "/$subfolder/$filename"
        )
    }

    // ------------------------------
    //  SAVE PDF TO DOWNLOADS/LaTeX
    // ------------------------------
    fun saveAsPdf(context: Context, image: ImageBitmap): File {
        val bitmap = image.toBitmap()
        val filename = "latex_${System.currentTimeMillis()}.pdf"

        val pdf = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(
            bitmap.width, bitmap.height, 1
        ).create()

        val page = pdf.startPage(pageInfo)
        page.canvas.drawBitmap(bitmap, 0f, 0f, null)
        pdf.finishPage(page)

        val downloadsDir = Environment.DIRECTORY_DOWNLOADS
        val subfolder = "LaTeX"

        val values = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, filename)
            put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
            put(MediaStore.Downloads.RELATIVE_PATH, "$downloadsDir/$subfolder")
            put(MediaStore.Downloads.IS_PENDING, 1)
        }

        val uri = context.contentResolver.insert(
            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
            values
        ) ?: throw Exception("Could not create PDF file")

        context.contentResolver.openOutputStream(uri).use { out ->
            pdf.writeTo(out!!)
        }
        pdf.close()

        values.clear()
        values.put(MediaStore.Downloads.IS_PENDING, 0)
        context.contentResolver.update(uri, values, null, null)

        return File(
            Environment.getExternalStoragePublicDirectory(downloadsDir)
                .absolutePath + "/$subfolder/$filename"
        )
    }

    // ------------------------------
    //  SHARE VIA PNG (Saved to Pictures first)
    // ------------------------------
    fun share(context: Context, image: ImageBitmap) {
        val file = saveAsImage(context, image)

        val uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

        // Requery MediaStore for the file URI
        val shareUri = context.contentResolver.query(
            uri,
            arrayOf(MediaStore.Images.Media._ID),
            "${MediaStore.Images.Media.DISPLAY_NAME}=?",
            arrayOf(file.name),
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val id = cursor.getLong(0)
                Uri.withAppendedPath(uri, id.toString())
            } else null
        } ?: return

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, shareUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(
            Intent.createChooser(intent, "Share LaTeX")
        )
    }
}
