package ru.iqsklad.ui.procedure.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import ru.iqsklad.R

class InvoiceScanOverlayView: View {

    private var finderMaskPaint: Paint? = null
    private var linesPaint: Paint? = null
    private var framingRect: Rect? = null

    companion object {
        private const val OFFSET_VERTICAL_BOUNDS = 50

        private const val PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE = 0.25
        private const val PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE = 0.75

        private const val PHONE_LEFT_RIGHT_MASK_RECT_PADDING = 100.0f
        private const val PHONE_CIRCLE_RADIUS = 150.0f
        private const val PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT = 15.0f
        private const val PHONE_LINES_LENGTH = 100.0f
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
       init()
    }

    private fun init() {
        finderMaskPaint = Paint()
        finderMaskPaint?.color = ContextCompat.getColor(context, R.color.grey_60)

        linesPaint = Paint()
        linesPaint?.color = ContextCompat.getColor(context, R.color.white)
        linesPaint?.strokeWidth = 7.0f
        linesPaint?.style = Paint.Style.STROKE
    }

    private fun getFramingRect(): Rect? = framingRect

    override fun onDraw(canvas: Canvas) {
        getFramingRect()?.let {
            drawViewFinderMask(canvas)
            drawCircle(canvas)
            drawAngles(canvas)
        }
    }

    private fun drawViewFinderMask(canvas: Canvas) {
        //draw on phone
        val width = canvas.width
        val height = canvas.height

        canvas.drawRect(
            0.0f,
            0.0f,
            width.toFloat(),
            (height * PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat(),
            finderMaskPaint!!)
        canvas.drawRect(
            0.0f,
            (height * PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat(),
            width.toFloat(),
            height.toFloat(),
            finderMaskPaint!!)
        canvas.drawRect(
            0.0f,
            (height * PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat(),
            PHONE_LEFT_RIGHT_MASK_RECT_PADDING,
            (height * PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat(),
            finderMaskPaint!!)
        canvas.drawRect(
            width.toFloat() - PHONE_LEFT_RIGHT_MASK_RECT_PADDING,
            (height * PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat(),
            width.toFloat(),
            (height * PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat(),
            finderMaskPaint!!)

        //todo case draw on tablet!!
    }

    private fun drawCircle(canvas: Canvas) {
        val width = canvas.width
        val height = canvas.height

        canvas.drawCircle((width * 0.5).toFloat(), (height * 0.5).toFloat(), PHONE_CIRCLE_RADIUS, linesPaint!!)
    }

    private fun drawAngles(canvas: Canvas) {
        val width = canvas.width
        val height = canvas.height

        //draw top left angle
        canvas.drawPoint(
            PHONE_LEFT_RIGHT_MASK_RECT_PADDING - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            linesPaint!!)
        canvas.drawLine(
            PHONE_LEFT_RIGHT_MASK_RECT_PADDING - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            PHONE_LEFT_RIGHT_MASK_RECT_PADDING - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT + PHONE_LINES_LENGTH,
            (height * PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            linesPaint!!)
        canvas.drawLine(
            PHONE_LEFT_RIGHT_MASK_RECT_PADDING - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            PHONE_LEFT_RIGHT_MASK_RECT_PADDING - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT + PHONE_LINES_LENGTH,
            linesPaint!!)

        //draw bottom left angle
        canvas.drawPoint(
            PHONE_LEFT_RIGHT_MASK_RECT_PADDING - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            linesPaint!!)
        canvas.drawLine(
            PHONE_LEFT_RIGHT_MASK_RECT_PADDING - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            PHONE_LEFT_RIGHT_MASK_RECT_PADDING - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT + PHONE_LINES_LENGTH,
            (height * PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            linesPaint!!)
        canvas.drawLine(
            PHONE_LEFT_RIGHT_MASK_RECT_PADDING - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            PHONE_LEFT_RIGHT_MASK_RECT_PADDING - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT - PHONE_LINES_LENGTH,
            linesPaint!!)

        //draw top right angle
        canvas.drawPoint(
            width.toFloat() - PHONE_LEFT_RIGHT_MASK_RECT_PADDING + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            linesPaint!!)
        canvas.drawLine(
            width.toFloat() - PHONE_LEFT_RIGHT_MASK_RECT_PADDING + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            width.toFloat() - PHONE_LEFT_RIGHT_MASK_RECT_PADDING + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT + PHONE_LINES_LENGTH,
            linesPaint!!)
        canvas.drawLine(
            width.toFloat() - PHONE_LEFT_RIGHT_MASK_RECT_PADDING + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            width.toFloat() - PHONE_LEFT_RIGHT_MASK_RECT_PADDING + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT - PHONE_LINES_LENGTH,
            (height * PHONE_TOP_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() - PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            linesPaint!!)

        //draw bottom right angle
        canvas.drawPoint(
            width.toFloat() - PHONE_LEFT_RIGHT_MASK_RECT_PADDING + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            linesPaint!!)
        canvas.drawLine(
            width.toFloat() - PHONE_LEFT_RIGHT_MASK_RECT_PADDING + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            width.toFloat() - PHONE_LEFT_RIGHT_MASK_RECT_PADDING + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT - PHONE_LINES_LENGTH,
            linesPaint!!)
        canvas.drawLine(
            width.toFloat() - PHONE_LEFT_RIGHT_MASK_RECT_PADDING + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            (height * PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            width.toFloat() - PHONE_LEFT_RIGHT_MASK_RECT_PADDING + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT - PHONE_LINES_LENGTH,
            (height * PHONE_BOTTOM_PERCENT_OF_SCREEN_MASK_RECT_SIZE).toFloat() + PHONE_ANGLE_LINES_MARGIN_FROM_MASK_RECT,
            linesPaint!!)
    }

    override fun onSizeChanged(xNew: Int, yNew: Int, xOld: Int, yOld: Int) {
        this.updateFramingRect()
    }

    @Synchronized
    fun updateFramingRect() {
        val viewResolution = Point(width, height)
        val width = width
        val height = (0.75f * width.toFloat()).toInt()

        val topOffset = (viewResolution.y - height) / 2
        framingRect = Rect(0, topOffset + OFFSET_VERTICAL_BOUNDS, width, topOffset + height - OFFSET_VERTICAL_BOUNDS)
    }
}