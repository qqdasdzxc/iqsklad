package ru.iqsklad.domain.source.keyboard

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class KeyboardStatusDataSource(activity: Activity) {

    private val activityRootView: View = activity.findViewById<View>(android.R.id.content)
    private var screenHeight = activityRootView.height
    // why are we using a global layout listener? Surely Android
    // has callback for when the keyboard is open or closed? Surely
    // Android at least lets you query the status of the keyboard?
    // Nope! https://stackoverflow.com/questions/4745988/
    private val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {

        val rect = Rect().apply { activityRootView.getWindowVisibleDisplayFrame(this) }

        val newScreenHeight = activityRootView.height
        when {
            //cases if window soft input mode is adjust resize
            newScreenHeight > screenHeight -> keyboardStateLiveData.postValue(KeyboardStatus.CLOSED)
            newScreenHeight < screenHeight -> keyboardStateLiveData.postValue(KeyboardStatus.OPEN)
            //case if not
            else -> {
                // rect.bottom is the position above soft keypad or device button.
                // if keypad is shown, the rect.bottom is smaller than that before.
                val keypadHeight = screenHeight - rect.bottom

                // 0.15 ratio is perhaps enough to determine keypad height.
                if (keypadHeight > screenHeight * 0.15) {
                    keyboardStateLiveData.postValue(KeyboardStatus.OPEN)
                } else {
                    keyboardStateLiveData.postValue(KeyboardStatus.CLOSED)
                }
            }
        }
    }

    private val keyboardStateLiveData = MutableLiveData<KeyboardStatus>()

    init {
        activityRootView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    fun getStateLiveData(): LiveData<KeyboardStatus> = keyboardStateLiveData

    fun release() = activityRootView.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)

}