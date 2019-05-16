package ru.iqsklad.domain.source

import android.media.Image
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata

class BarcodeImageAnalyzer : ImageAnalysis.Analyzer {

    private val barcodeDisplayValueLiveData = MutableLiveData<String>()

    private var barcodeDetector = FirebaseVision.getInstance().getVisionBarcodeDetector(
        FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_ALL_FORMATS)
            .build()
    )

    override fun analyze(image: ImageProxy, rotationDegrees: Int) {
        image.image?.let {
            val task = barcodeDetector.detectInImage(createFirebaseVisionImageFromMediaImage(it, rotationDegrees))
            task.addOnSuccessListener { result ->
                if (result.isNotEmpty()){
                    barcodeDisplayValueLiveData.postValue(result[0].displayValue)
                }
            }
            task.addOnFailureListener { exception ->
                exception.printStackTrace()
                barcodeDisplayValueLiveData.postValue(exception.localizedMessage)
            }
        }
    }

    private fun createFirebaseVisionImageFromMediaImage(image: Image, rotationDegrees: Int): FirebaseVisionImage {
        return FirebaseVisionImage.fromMediaImage(image, getFirebaseRotation(rotationDegrees))
    }

    private fun getFirebaseRotation(rotationDegrees: Int): Int {
        return when (rotationDegrees) {
            0 -> FirebaseVisionImageMetadata.ROTATION_0
            90 -> FirebaseVisionImageMetadata.ROTATION_90
            180 -> FirebaseVisionImageMetadata.ROTATION_180
            270 -> FirebaseVisionImageMetadata.ROTATION_270
            else -> {
                FirebaseVisionImageMetadata.ROTATION_0
            }
        }
    }

    fun getBarcodeDisplayValueLiveData(): LiveData<String?> = barcodeDisplayValueLiveData
}