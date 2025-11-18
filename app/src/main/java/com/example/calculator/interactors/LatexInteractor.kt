package com.example.calculator.interactors

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.example.calculator.models.ExportRequestType
import com.example.calculator.repository.LatexRepository

class LatexInteractor(
    private val repository: LatexRepository
){
    fun exportLatex(requestType: ExportRequestType, image: ImageBitmap, context: Context){
        when (requestType) {
            ExportRequestType.SAVE_AS_IMAGE -> repository.saveAsImage(context, image)
            ExportRequestType.SAVE_AS_PDF -> repository.saveAsPdf(context, image)
            ExportRequestType.SHARE -> repository.share(context, image)
        }
    }
}