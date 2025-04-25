package com.flatify.mangaverse.presentation.ui.FaceRecognition

/*
 * Copyright 2023 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.flatify.mangaverse.R
import com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult
import kotlin.math.min

class OverlayView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private var results: FaceDetectorResult? = null
    private var boxPaint = Paint()
    private var textBackgroundPaint = Paint()
    private var textPaint = Paint()

    private var scaleFactor: Float = 1f

    private var bounds = Rect()

    private lateinit var referenceRect: RectF
    private var isInside: Boolean = false

    init {
        initPaints()
    }

    fun clear() {
        results = null
        textPaint.reset()
        textBackgroundPaint.reset()
        boxPaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        textBackgroundPaint.color = Color.BLACK
        textBackgroundPaint.style = Paint.Style.FILL
        textBackgroundPaint.textSize = 50f

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 50f

        boxPaint.color = ContextCompat.getColor(context!!, R.color.green)
        boxPaint.strokeWidth = 8F
        boxPaint.style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Make a square centered rectangle, about 60% of the view's width or height
        val size = (min(w, h) * 0.8f)
        val left = (w - size) / 2f
        val top = (h - size) / 2f
        referenceRect = RectF(left, top, left + size, top + size)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

//        results?.let {
//            for (detection in it.detections()) {
//                val boundingBox = detection.boundingBox()
//
//                val top = boundingBox.top * scaleFactor
//                val bottom = boundingBox.bottom * scaleFactor
//                val left = boundingBox.left * scaleFactor
//                val right = boundingBox.right * scaleFactor
//
//                // Draw bounding box around detected faces
//                val drawableRect = RectF(left, top, right, bottom)
//                canvas.drawRect(drawableRect, boxPaint)
//
//                // Create text to display alongside detected faces
//                val drawableText =
//                    detection.categories()[0].categoryName() +
//                            " " +
//                            String.format(
//                                "%.2f",
//                                detection.categories()[0].score()
//                            )
//
//                // Draw rect behind display text
//                textBackgroundPaint.getTextBounds(
//                    drawableText,
//                    0,
//                    drawableText.length,
//                    bounds
//                )
//                val textWidth = bounds.width()
//                val textHeight = bounds.height()
//                canvas.drawRect(
//                    left,
//                    top,
//                    left + textWidth + Companion.BOUNDING_RECT_TEXT_PADDING,
//                    top + textHeight + Companion.BOUNDING_RECT_TEXT_PADDING,
//                    textBackgroundPaint
//                )
//
//                // Draw text for detected face
//                canvas.drawText(
//                    drawableText,
//                    left,
//                    top + bounds.height(),
//                    textPaint
//                )
//            }
//        }

        referenceRect?.let {
            val paint = Paint().apply {
                style = Paint.Style.STROKE
                strokeWidth = 8f
                color = if (isInside) Color.GREEN else Color.RED
            }
            canvas.drawRect(it, paint)
        }
    }



    fun setResults(
        detectionResults: FaceDetectorResult,
        imageHeight: Int,
        imageWidth: Int,
        isInside: Boolean
    ) {
        results = detectionResults

        val faceBoundingBoxes = results!!.detections().map { it.boundingBox() }
        if(faceBoundingBoxes.isEmpty()){
            this.isInside = false
        }else{
            val faceCenterX = faceBoundingBoxes[0].centerX()
            val faceCenterY = faceBoundingBoxes[0].centerY()

            this.isInside = referenceRect.contains(faceCenterX, faceCenterY)
            if(referenceRect.contains(faceCenterX, faceCenterY) == true){
                Log.d("BOX", isInside.toString())
            }
        }


        // Images, videos and camera live streams are displayed in FIT_START mode. So we need to scale
        // up the bounding box to match with the size that the images/videos/live streams being
        // displayed.
        scaleFactor = min(width * 1f / imageWidth, height * 1f / imageHeight)

        invalidate()
    }

    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
    }
}