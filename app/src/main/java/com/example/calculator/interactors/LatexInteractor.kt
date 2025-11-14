package com.example.calculator.interactors

import androidx.compose.ui.graphics.ImageBitmap
import com.example.calculator.models.ExportRequestType
import com.example.calculator.repository.LatexRepository

class LatexInteractor(
    private val repository: LatexRepository
){
    fun exportLatex(requestType: ExportRequestType, image: ImageBitmap){
        when (requestType) {
            ExportRequestType.SAVE_AS_IMAGE -> repository.saveAsImage(image)
            ExportRequestType.SAVE_AS_PDF -> repository.saveAsPdf(image)
            ExportRequestType.SHARE -> repository.share(image)
        }
    }
}