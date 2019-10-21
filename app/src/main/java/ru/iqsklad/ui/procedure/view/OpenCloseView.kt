package ru.iqsklad.ui.procedure.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView

class OpenCloseView : AppCompatImageView, View.OnClickListener {
    private var isExpand: Boolean = false
    private var listener: OpenCloseChangeListener? = null

    companion object {
        const val ANGLE_ARROW = 90F
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        setOnClickListener(this)
    }

    private fun updateItem() {
        animate().rotation(if (isExpand) ANGLE_ARROW else ANGLE_ARROW * -1).start()
    }

    override fun onClick(v: View?) {
        isExpand = !isExpand
        if (isExpand) {
            listener?.onOpen()
        } else {
            listener?.onClose()
        }
        updateItem()
    }

    fun setListener(listener: OpenCloseChangeListener) {
        this.listener = listener
    }

    interface OpenCloseChangeListener {
        fun onOpen()
        fun onClose()
    }
}