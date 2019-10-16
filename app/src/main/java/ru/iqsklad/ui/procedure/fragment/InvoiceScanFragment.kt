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
import androidx.camera.core.*
import androidx.lifecycle.Observer
import ru.iqsklad.R
import ru.iqsklad.data.dto.ui.ErrorUiModel
import ru.iqsklad.data.dto.ui.LoadingUiModel
import ru.iqsklad.data.dto.ui.SuccessUiModel
import ru.iqsklad.databinding.FragmentInvoiceScanBinding
import ru.iqsklad.domain.source.BarcodeImageAnalyzer
import ru.iqsklad.presentation.implementation.procedure.FindInvoiceViewModel
import ru.iqsklad.presentation.presenter.procedure.FindInvoicePresenter
import ru.iqsklad.ui.base.activity.BaseActivity
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.utils.PermissionUtils
import ru.iqsklad.utils.extensions.safeNavigate

class InvoiceScanFragment : BaseFragment<FragmentInvoiceScanBinding>() {

    private var analyzerUseCase: ImageAnalysis? = null
    private lateinit var presenter: FindInvoicePresenter

    override fun getLayoutResId(): Int = R.layout.fragment_invoice_scan

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = getPresenter<FindInvoiceViewModel>().apply {
            refreshInvoices(activity!!)

            getFindingInvoiceResult().observe(this@InvoiceScanFragment, Observer { uiModel ->
                when (uiModel) {
                    LoadingUiModel -> {}//showMessage("Поиск накладной в базе...")
                    is SuccessUiModel -> {
                        if (uiModel.data == null) {
                            showMessage("Накладная не найдена! Попробуйте еще раз")
                        } else {
                            setProcedureInvoice(uiModel.data)
                            navigateToInventoryScan()
                        }
                    }
                    is ErrorUiModel -> showMessage(uiModel.error)
                }
            })
        }
    }

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

    private fun navigateToInventoryScan() {
        safeNavigate(R.id.invoice_scan_fragment) {
            navController.navigate(InvoiceScanFragmentDirections.actionInvoiceScanToInventoryScan())
        }
    }

    private fun initCamera() {
        val metrics = DisplayMetrics().also { binding.invoiceScanTextureView.display.getRealMetrics(it) }
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(Rational(metrics.widthPixels, metrics.heightPixels))
            setTargetResolution(Size(metrics.widthPixels, metrics.heightPixels))
            setLensFacing(CameraX.LensFacing.BACK)
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
        barcodeImageAnalyzer.getBarcodeDisplayValueLiveData().observe(this, Observer { barcodeString ->
            barcodeString?.let { handleScannedBarcodeValue(it) }
        })

        analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
            analyzer = barcodeImageAnalyzer
        }

        CameraX.bindToLifecycle(this, preview, analyzerUseCase)
    }

    private fun handleScannedBarcodeValue(barcodeString: String) {
        presenter.findInvoice(barcodeString)
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