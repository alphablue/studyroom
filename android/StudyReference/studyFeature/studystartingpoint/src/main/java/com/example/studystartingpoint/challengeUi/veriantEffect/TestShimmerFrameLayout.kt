package com.example.studystartingpoint.challengeUi.veriantEffect

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.studystartingpoint.util.d

class TestShimmerFrameLayout(
    context: Context,
    attrs: AttributeSet
) : FrameLayout(context, attrs) {

    private val testShimmerDrawable = TestShimmerDrawable(testShimmer = TestShimmerObj())

    init {
        setWillNotDraw(false)
        testShimmerDrawable.callback = this
        setLayerType(LAYER_TYPE_HARDWARE, Paint())
    }

    fun startShimmer() {
        if (isAttachedToWindow) {
            testShimmerDrawable.startShimmer()
        }
    }

    fun stopShimmer() {
        testShimmerDrawable.stopShimmer()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        "layout onLayout IN $left , $top , $right , $bottom, $changed".d("shimmerTest")
        testShimmerDrawable.setBounds(0, 0, width, height)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (!isVisible) {
            if (testShimmerDrawable.isShimmerStarted()) {
                stopShimmer()
            }
        } else {
            testShimmerDrawable.maybeStartShimmer()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        testShimmerDrawable.maybeStartShimmer()

        "layout Attach IN".d("shimmerTest")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopShimmer()
    }

    /**
     * 이 함수에 drawable 을 연결해야지만 drawable의 invalidateSelf()일때 draw() 함수가 호출 됨
     * */
    override fun verifyDrawable(who: Drawable): Boolean {
        return super.verifyDrawable(who) || who is TestShimmerDrawable
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        testShimmerDrawable.draw(canvas)

        "layout draw IN".d("shimmerTest")
        startShimmer()
    }
}