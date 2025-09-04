package com.example.studystartingpoint.challengeUi.veriantEffect

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator
import com.example.studystartingpoint.util.d
import kotlin.math.max
import kotlin.math.min
import kotlin.math.tan

class TestShimmerDrawable(
    private val testShimmerPaint: Paint = Paint(),
    private val drawRect: Rect = Rect(),
    private val shaderMatrix: Matrix = Matrix(),
    val testShimmer: TestShimmerObj
) : Drawable() {

    var valueAnimator: ValueAnimator? = null
    var staticAnimationProgress = -1f

    init {
        testShimmerPaint.isAntiAlias = true
        testShimmerPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)

        updateShader()
        updateValueAnimator()
        invalidateSelf()
    }

    override fun draw(canvas: Canvas) {
        "is Called draw()".d("animTest")
        if (testShimmerPaint.shader == null) return

//        val titleTan = tan(20f) // 20 라디안
        val titleTanRadian = tan(Math.toRadians(20.0)).toFloat() // 20 도를 라디안으로 변환

        val translateHeight = drawRect.height() + titleTanRadian * drawRect.width()
        val translateWidth = drawRect.width() + titleTanRadian * drawRect.height()

        val animatedValue = if (staticAnimationProgress < 0f) {
            valueAnimator?.run { animatedValue as Float } ?: 0f
        } else {
            staticAnimationProgress
        }

        "[Drawable] draw IN (rect) : ${drawRect.height()}, ${drawRect.width()}".d("shimmerTest")
        "[Drawable] draw IN (normal) : $titleTanRadian, $translateHeight, $translateWidth, $animatedValue".d("shimmerTest")

        val (dx, dy) = when (testShimmer.direction) {
            TestShimmerObj.Direction.LEFT_TO_RIGHT -> {
                offset(-translateWidth, translateWidth, animatedValue) to 0f
            }

            TestShimmerObj.Direction.RIGHT_TO_LEFT -> {
                offset(translateWidth, -translateWidth, animatedValue) to 0f
            }

            TestShimmerObj.Direction.TOP_TO_BOTTOM -> {
                0f to offset(-translateHeight, translateHeight, animatedValue)
            }

            TestShimmerObj.Direction.BOTTOM_TO_TOP -> {
                0f to offset(translateHeight, -translateHeight, animatedValue)
            }
        }

        shaderMatrix.apply {
            reset()
            setRotate(20f, drawRect.width() / 2f, drawRect.height() / 2f)
            preTranslate(dx, dy)
        }
        testShimmerPaint.shader.setLocalMatrix(shaderMatrix)
        canvas.drawRect(drawRect, testShimmerPaint)

        "[Drawable] draw IN : $drawRect , $dx, $dy".d("shimmerTest")
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        drawRect.set(bounds)
        updateShader()
        // 용도 확인
//        maybeStartShimmer()

        "[Drawable] bounds change IN : $bounds".d("shimmerTest")
    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    /**
     * drawable 객체의 투명도를 설정하는 것,
     * */
    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    private fun offset(start: Float, end: Float, percent: Float): Float {
        return start + (end - start) * percent
    }

    fun startShimmer() {
        if (!isShimmerStarted()) {
            valueAnimator?.start()
        }
    }

    fun isShimmerStarted(): Boolean {
        return valueAnimator?.isStarted ?: false
    }

    fun stopShimmer() {
        if (isShimmerStarted()) {
            valueAnimator?.cancel()
        }
    }

    fun isShimmerRunning(): Boolean {
        return valueAnimator?.isRunning ?: false
    }

    fun maybeStartShimmer() {
        if (valueAnimator != null
            && !(valueAnimator?.isStarted ?: false)
            && callback != null
        ) {
            valueAnimator?.start()
        }
    }

    fun updateValueAnimator() {
        var started = false

        valueAnimator?.run {
            started = isStarted
            cancel()
            removeAllUpdateListeners()
        }

        // repeatDelay 와 duration 의 설정에 따라서 다른 형태가 되도록 조절 가능함
        valueAnimator = ValueAnimator.ofFloat(0f, 1f + (100L/3000)).apply {
            interpolator = LinearInterpolator()
            repeatMode = testShimmer.repeatMode
//            startDelay = testShimmer.startDelay
            repeatCount = testShimmer.repeatCount
//            duration = testShimmer.animationDuration + testShimmer.repeatDelay
            startDelay = 100L
            duration = 3000L

            addUpdateListener {
                // animation 이 작동은 하고 있음, 근데 애니메이션의 동작을 잘못 설정한거 같음
//                "animation listener check".d("animTest")
                invalidateSelf()
            }
        }

        if (started) {
            valueAnimator?.start()
        }
    }

    fun updateShader() {
        val bounds = bounds
        val boundsWidth = bounds.width()
        val boundsHeight = bounds.height()

        if (boundsWidth == 0 || boundsHeight == 0) return

        val width = testShimmer.width(boundsWidth)
        val height = testShimmer.height(boundsHeight)

        "[Drawable] shared IN : $bounds, $boundsWidth, $boundsHeight, $width , $height".d("shimmerTest")

        val shader = when (testShimmer.shape) {
            TestShimmerObj.ShimmerShape.LINEAR -> {
                val vertical = testShimmer.direction == TestShimmerObj.Direction.TOP_TO_BOTTOM
                        || testShimmer.direction == TestShimmerObj.Direction.BOTTOM_TO_TOP

                val endX = if (vertical) 0f else width
                val endY = if (vertical) height else 0f

                "[Drawable] shared IN : $endX , $endY".d("shimmerTest")

                LinearGradient(
                    0f, 0f, endX, endY,
                    intArrayOf(
                        0x4cffffff,
                        Color.WHITE,
                        Color.WHITE,
                        0x4cffffff
                    ),
                    floatArrayOf(
                        max((1f - 0f - 0.5f) / 2f, 0f),
                        max((1f - 0f - 0.01f) / 2f, 0f),
                        min((1f + 0f + 0.01f) / 2f, 1f),
                        min((1f + 0f + 0.5f) / 2f, 1f)
                    ),
                    Shader.TileMode.CLAMP
                )

                LinearGradient(
                    0f, 0f, endX, endY, testShimmer.colors.toIntArray(), testShimmer.positions.toFloatArray(), Shader.TileMode.CLAMP
                )
            }
        }

        testShimmerPaint.shader = shader
    }
}