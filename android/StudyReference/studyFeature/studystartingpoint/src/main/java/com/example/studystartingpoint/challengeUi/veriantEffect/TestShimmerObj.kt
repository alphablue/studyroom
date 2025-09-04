package com.example.studystartingpoint.challengeUi.veriantEffect

import android.animation.ValueAnimator
import android.graphics.RectF
import androidx.core.graphics.toColorInt
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
//    var sColor = ContextCompat.getColor(WhoWhoApp.instance, R.color.colorTestGradientStart)
//    var cColor = ContextCompat.getColor(WhoWhoApp.instance, R.color.colorTestGradientCenter)
//    var eColor = ContextCompat.getColor(WhoWhoApp.instance, R.color.colorTestGradientEnd)

    // 아래 처럼 hex color 를 직접대입하면 colorInt 값으로 인식할 수 없음, 확장함수로 제공하는 color.parseColor() 를 활용하자
//    var startColor: UInt = 0xff0075ff
//    var centerColor: Int = 0xff9763e2
//    var endColor: Int = 0xffffb86c

    var startColor: Int = "#000000".toColorInt()
    var betweenColor: Int = "#00ffffff".toColorInt()
    var centerColor: Int = "#ffffff".toColorInt()
    var endColor: Int = "#000000".toColorInt()

    val colors: MutableList<Int> = mutableListOf(startColor, betweenColor, centerColor, centerColor, betweenColor, endColor)
    val positions: MutableList<Float> = mutableListOf(
        max((1f - 0f - 0.5f) / 2f, 0f),
        max((1f - 0f - 0.42f) / 2f, 0f),
        max((1f - 0.01f) / 2f, 0f),
        min((1f + 0.01f) / 2f, 1f),
        min((1f + 0f + 0.42f) / 2f, 1f),
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