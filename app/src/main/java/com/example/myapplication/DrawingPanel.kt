package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs
import kotlin.math.max

class DrawingPanel(context: Context, attrs: AttributeSet?) : View(context, attrs){

    constructor(context: Context) : this(context, null)

    private val bgColor = 0xffffffe0.toInt()
    private val fgColor = 0xff00ff00.toInt()

    private val bgPaint = Paint()
    private val fgPaint = Paint()

    init {
        bgPaint.color = bgColor
        fgPaint.color = fgColor
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.let{ cnv ->
            cnv.drawPaint(bgPaint)
            center?.let{ c->
                cnv.drawCircle(c.x.toFloat(), c.y.toFloat(), radius, fgPaint)
            }
        }
    }

    private var center: Point? = null
    private var touched = false
    private var radius = 0F

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action){
            MotionEvent.ACTION_DOWN ->{
                center = Point(event.x.toInt(), event.y.toInt())
                touched = true
            }
            MotionEvent.ACTION_UP ->{
               touched = false
            }
            MotionEvent.ACTION_MOVE ->{
               if (touched){
                   center?.let {
                       radius = max(abs(event.x.toInt() - it.x), abs(event.y.toInt() - it.y)).toFloat()
                   }
                   invalidate()
               }
            }
        }

        return true
    }
}