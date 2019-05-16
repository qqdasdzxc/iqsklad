package ru.iqsklad.utils

import android.Manifest
import androidx.annotation.StringRes
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener
import ru.iqsklad.R
import ru.iqsklad.ui.base.activity.BaseActivity


class PermissionUtils {

    fun checkCameraPermission(activity: BaseActivity, actionListener: ActionListener) {
        val requiredPermissions = arrayListOf(
            Manifest.permission.CAMERA
        )
        checkWithCustomDeniedAction(activity, actionListener, requiredPermissions)
    }

    private fun check(activity: BaseActivity, actionListener: ActionListener?, requiredPermissions: ArrayList<String>) {
        val compositePermissionsListener: CompositeMultiplePermissionsListener
        val permissionsListener = buildCheckedPermissionsListener(requiredPermissions, actionListener)

        compositePermissionsListener = if (actionListener != null && actionListener.onMessageDeniedResId() != 0) {
            val messageDeniedListener =
                buildMessageDeniedPermissionsListener(activity, actionListener.onMessageDeniedResId())
            CompositeMultiplePermissionsListener(permissionsListener, messageDeniedListener)
        } else {
            CompositeMultiplePermissionsListener(permissionsListener)
        }

        Dexter.withActivity(activity)
            .withPermissions(requiredPermissions)
            .withListener(compositePermissionsListener)
            .check()
    }

    private fun buildCheckedPermissionsListener(
        requiredPermissions: ArrayList<String>,
        actionListener: ActionListener?
    ): MultiplePermissionsListener {

        return object : MultiplePermissionsListener {

            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                val isPresentDenied = report?.deniedPermissionResponses?.firstOrNull { response ->
                    requiredPermissions.contains(response.permissionName)
                }

                isPresentDenied?.let {
                    actionListener?.onAcceptAction()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }
        }
    }

    private fun buildMessageDeniedPermissionsListener(activity: BaseActivity, @StringRes messageResId: Int): MultiplePermissionsListener {
        return SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
            .with(activity.findViewById(android.R.id.content), messageResId)
            .withOpenSettingsButton(R.string.tune)
            .build()
    }

    private fun checkWithCustomDeniedAction(
        activity: BaseActivity,
        actionListener: ActionListener?,
        requiredPermissions: ArrayList<String>
    ) {
        val compositePermissionsListener: CompositeMultiplePermissionsListener
        val permissionsListener = buildCheckedPermissionsListenerWithCustomDeniedAction(requiredPermissions, actionListener)

        compositePermissionsListener = CompositeMultiplePermissionsListener(permissionsListener)

        Dexter.withActivity(activity)
            .withPermissions(requiredPermissions)
            .withListener(compositePermissionsListener)
            .check()
    }

    private fun buildCheckedPermissionsListenerWithCustomDeniedAction(
        requiredPermissions: ArrayList<String>,
        actionListener: ActionListener?
    ): MultiplePermissionsListener {

        return object : MultiplePermissionsListener {

            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                val deniedPermission = report?.deniedPermissionResponses?.firstOrNull { response ->
                    requiredPermissions.contains(response.permissionName)
                }

                deniedPermission?.let {
                    actionListener?.onDeniedAction()
                    return
                }

                actionListener?.onAcceptAction()
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }
        }
    }

    interface ActionListener {

        fun onAcceptAction()

        fun onDeniedAction() {}

        fun onMessageDeniedResId(): Int {
            return 0
        }
    }
}