package ru.iqsklad.ui.procedure.fragment

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
import ru.iqsklad.databinding.FragmentInvoiceScanBinding
import ru.iqsklad.domain.source.BarcodeImageAnalyzer
import ru.iqsklad.ui.base.activity.BaseActivity
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.utils.PermissionUtils

class InvoiceScanFragment : BaseFragment<FragmentInvoiceScanBinding>() {

    var analyzerUseCase: ImageAnalysis? = null

    override fun getLayoutResId(): Int = R.layout.fragment_invoice_scan

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onStart() {
        super.onStart()

        checkPermissions()
    }

    override fun onPause() {
        super.onPause()

        analyzerUseCase?.removeAnalyzer()
        CameraX.unbindAll()
    }

    private fun checkPermissions() {
        PermissionUtils().checkCameraPermission(
            activity!! as BaseActivity,
            object : PermissionUtils.ActionListener {
                override fun onAcceptAction() {
                    binding.invoiceScanTextureView.post { initCamera() }
                }

                override fun onDeniedAction() {
                    navigateToInvoiceNumberInput()
                }

                override fun onMessageDeniedResId(): Int {
                    return R.string.invoice_scan_permission_message_dialog
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

        analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
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
        val rotationDegrees = when (binding.invoiceScanTextureView.display.rotation) {
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