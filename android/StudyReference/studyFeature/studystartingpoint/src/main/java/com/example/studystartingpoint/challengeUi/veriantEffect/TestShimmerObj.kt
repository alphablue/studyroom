package com.ktcs.whowho.customlayout

import android.animation.ValueAnimator
import android.graphics.RectF
import androidx.annotation.ColorInt
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

class TestShimmerObj {

    /**
     * shimmer 의 형태
     * */
    enum class ShimmerShape {
        LINEAR,
    }

    /**
     * gradient 된 배경의 애니메이션이 이동하는 방향 정의
     */
    enum class Direction {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT,
        TOP_TO_BOTTOM,
        BOTTOM_TO_TOP
    }

    /**
     * gradient의 3가지 색상 정의
     * */
    @ColorInt
    var startColor: Int = 0x4c0075ff
    @ColorInt
    var centerColor: Int = 0x4c9763e2
    @ColorInt
    var endColor: Int = 0x4cffb86c
    val colors: MutableList<Int> = mutableListOf(startColor, centerColor, endColor)
    val positions: MutableList<Float> = mutableListOf(
        max((1f - 0f - 0.5f) / 2f, 0f),
        min((1f + 0f + 0.01f) / 2f, 1f),
        min((1f + 0f + 0.5f) / 2f, 1f)
    )

    val bounds: RectF = RectF()
    var shape: ShimmerShape = ShimmerShape.LINEAR
    var direction: Direction = Direction.LEFT_TO_RIGHT

    /**
     * 애니메이션 실행 옵션
     * */
    var animationDuration = 1000L
    var repeatCount = ValueAnimator.INFINITE
    var repeatMode = ValueAnimator.RESTART

    fun width(width: Int) = round(1f * width)
    fun height(width: Int) = round(1f * width)
}