package ru.iqsklad.ui.procedure.fragment

import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.DisplayMetrics
import android.util.Rational
import android.util.Size
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.lifecycle.Observer
import ru.iqsklad.R
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.databinding.FragmentInvoiceScanBinding
import ru.iqsklad.domain.source.BarcodeImageAnalyzer
import ru.iqsklad.ui.base.activity.BaseActivity
import ru.iqsklad.utils.PermissionUtils

class InvoiceScanFragment: BaseFragment<FragmentInvoiceScanBinding>() {

    override fun getLayoutResId(): Int = R.layout.fragment_invoice_scan

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    override fun onResume() {
        super.onResume()

        checkCameraPermission()
    }

    override fun onPause() {
        super.onPause()

        CameraX.unbindAll()
    }

    private fun checkCameraPermission() {
        PermissionUtils().checkCameraPermission(activity!! as BaseActivity, object : PermissionUtils.ActionListener {
            override fun onAcceptAction() {
                //Note: Instead of calling `initCamera()` on the main thread, we use `viewFinder.post { ... }`
                //to make sure that `viewFinder` has already been inflated into the view when `initCamera()` is called.
                binding.invoiceScanTextureView.post { initCamera() }
            }

            override fun onDeniedAction() {
                navigateToInvoiceNumberInput()
            }
        })
    }

    private fun initView() {
        binding.invoiceScanErrorActionTitle.setOnClickListener {
            navigateToInvoiceNumberInput()
        }
    }

    private fun navigateToInvoiceNumberInput() {
        navController.navigate(InvoiceScanFragmentDirections.actionInvoiceScanToInvoiceNumberInput())
    }

    private fun initCamera() {
        val metrics = DisplayMetrics().also { binding.invoiceScanTextureView.display.getRealMetrics(it) }
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(Rational(metrics.widthPixels, metrics.heightPixels))
            setTargetResolution(Size(640, 640))
        }.build()

        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {

            val parent = binding.invoiceScanTextureView.parent as ViewGroup
            parent.removeView(binding.invoiceScanTextureView)
            parent.addView(binding.invoiceScanTextureView, 0)

            binding.invoiceScanTextureView.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
            val analyzerThread = HandlerThread("Barcode analyzer").apply { start() }
            setCallbackHandler(Handler(analyzerThread.looper))
            setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
        }.build()

        val barcodeImageAnalyzer = BarcodeImageAnalyzer()
        barcodeImageAnalyzer.getBarcodeDisplayValueLiveData().observe(this, Observer {
            Toast.makeText(activity!!, it, Toast.LENGTH_SHORT).show()
        })

        val analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
            analyzer = barcodeImageAnalyzer
        }

        CameraX.bindToLifecycle(this, preview, analyzerUseCase)
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = binding.invoiceScanTextureView.width / 2f
        val centerY = binding.invoiceScanTextureView.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when(binding.invoiceScanTextureView.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        binding.invoiceScanTextureView.setTransform(matrix)
    }

    override fun handleScanPressButton() {}
}