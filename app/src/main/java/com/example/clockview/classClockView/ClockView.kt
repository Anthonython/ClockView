package com.example.clockview.classClockView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*

class ClockView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val calendar: Calendar = Calendar.getInstance()
    private var hour: Int = 0
    private var minute: Int = 0
    private var second: Int = 0

    private val paint = Paint()
    private val textPaint = Paint()
    private val numbers = arrayOf("3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "1", "2")
    private val hand: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            updateClock()
            sendEmptyMessageDelayed(0, 1000)
        }
    }

    init {
        paint.isAntiAlias = true
        paint.color = Color.CYAN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        textPaint.isAntiAlias = true
        textPaint.color = Color.BLACK
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = 50f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = width / 2f

        // Циферблат
        paint.strokeWidth = 12f
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(radius, radius, radius - paint.strokeWidth / 2f, paint)


        // Цифры
        paint.strokeWidth = 4f
        for (i in 0 until 12) {
            val x = radius + (radius - 70f) * Math.cos(Math.toRadians((i * 30).toDouble())).toFloat()
            val y = radius + (radius - 70f) * Math.sin(Math.toRadians((i * 30).toDouble())).toFloat()
            canvas.drawText(numbers[i], x, y + textPaint.textSize / 3f, textPaint)
        }

        // Центральная точка
        paint.style = Paint.Style.FILL
        canvas.drawCircle(radius, radius, 12f, paint)

        // Часовая стрелка
        paint.strokeWidth = 8f
        paint.color = Color.RED
        canvas.save()
        canvas.rotate((hour + minute / 60f) * 30f, radius, radius)
        canvas.drawLine(radius, radius + 20f, radius, radius - radius / 2f, paint)
        canvas.restore()

        // Минутная
        paint.strokeWidth = 4.5f
        canvas.save()
        canvas.rotate(minute * 6f, radius, radius)
        canvas.drawLine(radius, radius + 20f, radius, radius - radius * 0.7f, paint)
        canvas.restore()

        // Секундная
        paint.strokeWidth = 2f
        canvas.save()
        canvas.rotate(second * 6f, radius, radius)
        canvas.drawLine(radius, radius + 20f, radius, radius - radius * 0.9f, paint)
        canvas.restore()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        hand.sendEmptyMessage(0)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        hand.removeCallbacksAndMessages(null)
    }

    private fun updateClock() {
        calendar.timeInMillis = System.currentTimeMillis()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        second = calendar.get(Calendar.SECOND)
        invalidate()
    }

}