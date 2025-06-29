//package com.example.studystartingpoint.challengeUi.text
//
//import androidx.compose.animation.core.LinearEasing
//import androidx.compose.animation.core.RepeatMode
//import androidx.compose.animation.core.animateFloat
//import androidx.compose.animation.core.infiniteRepeatable
//import androidx.compose.animation.core.rememberInfiniteTransition
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.drawBehind
//import androidx.compose.ui.geometry.Rect
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.drawscope.DrawScope
//import androidx.compose.ui.text.SpanStyle
//import androidx.compose.ui.text.buildAnnotatedString
//import androidx.compose.ui.text.withAnnotation
//import androidx.compose.ui.text.withStyle
//import androidx.compose.ui.unit.Density
//import androidx.compose.ui.unit.TextUnit
//import androidx.compose.ui.unit.sp
//import com.example.studystartingpoint.ui.theme.Purple
//import com.example.studystartingpoint.util.d
//import kotlin.contracts.ExperimentalContracts
//import kotlin.contracts.contract
//import kotlin.math.PI
//import kotlin.math.ceil
//import kotlin.math.sin
//import kotlin.time.Duration.Companion.seconds
//
//
//
//@Composable
//fun UnderlineDrawingEntryPoint(innerPadding: PaddingValues) {
//    var onDraw: DrawScope.() -> Unit by remember { mutableStateOf({}) }
//
//    Column(
//        Modifier
//            .padding(innerPadding)
//            .fillMaxSize()
//    ) {
//        Row(
//            modifier = Modifier.fillMaxSize(),
//        ) {
//            Text(
//                modifier = Modifier
//                    .weight(1f)
//                    .customViewCheck(),
//                color = Color.Yellow,
//                text = "월드 월드 hello hello hello hello hello hello hello hello"
//            )
//
//            val textAnnotation = buildAnnotatedString {
//                append("텍스트 입니다.")
//                withAnnotation("태그", annotation = "어노테이션") {
//                    withStyle(SpanStyle(color = Purple, fontSize = 24.sp)) {
//                        append("냠냠냠 맛있다.") // 8글자
//                    }
//                }
//            }
//
//            Text(
//                modifier = Modifier
//                    .weight(1f)
//                    .drawBehind { onDraw() },
//                text = textAnnotation,
//                onTextLayout = { textLayout ->
//
//                    // stringBuilder 에서 annotation을 정의한 값을 가져온다. 만들어진 글자의 index 중 start 와 end 사이에 tag에 해당하는 내용이 있다면 추출된다. 없다면 크래시
//                    val annotation =
//                        textAnnotation.getStringAnnotations("태그", 0, textAnnotation.lastIndex).let {
//                            it.forEach { list ->
//                                list.toString().d("customText")
//                            }
//
//                            it.first()
//                        }
//
//                    val textBounds = mutableListOf<Rect>()
//
//                    textAnnotation.indexOf("냠냠냠 맛있다.").let {
//                        for (idx in it until it + 8) {
//                            val boundBox = textLayout.getBoundingBox(idx)
//                            textBounds.add(boundBox)
//                            "text bounds : $boundBox".d("customText")
//                        }
//                    }
//
//                    onDraw = {
//                        for (bound in textBounds) {
//                            val underline =
//                                bound.copy(top = bound.bottom * 0.93f) // top 을 bottom 에서 일정 비율만큼 빼도록 하기 위한 값 설정, bottom - top 이 size 에서 height 값이 된다.
//
//                            // span style
//                            val spanStyle = textAnnotation.spanStyles.first { range ->
//                                maxOf(
//                                    annotation.start,
//                                    range.start
//                                ) < minOf(annotation.end, range.end)
//                            }
//                            "first: ${spanStyle.item.fontSize}".d("checkSize")
//
//                            "bound: $bound, underlineBound: $underline".d("checkSize")
//                            "offset (top) : ${bound.topLeft} , ${bound.topCenter} , ${bound.topRight}".d(
//                                "checkSize"
//                            )
//                            "offset (center) : ${bound.centerLeft} , ${bound.center} , ${bound.centerRight}".d(
//                                "checkSize"
//                            )
//                            "offset (bottom) : ${bound.bottomLeft} , ${bound.bottomCenter} , ${bound.bottomRight}".d(
//                                "checkSize"
//                            )
//
////                                            drawRect(
////                                                color = Purple,
////                                                topLeft = underline.topLeft,
////                                                size = underline.size // width , height 값을 반환
////                                            )
//
////                                            drawPath(
////                                                path = Path(
////
////                                                )
////                                            )
//                        }
//                    }
//                }
//            )
//        }
//    }
//}
//
//
//private val TWO_PI = 2 * PI.toFloat()
//private val SEGMENTS_PER_WAVELENGTH = 10
//
//private fun Path.buildSquigglesFor(
//    box: Rect,
//    density: Density,
//    width: TextUnit = 4.sp,
//    wavelength: TextUnit = 10.sp,
//    amplitude: TextUnit = 10.sp,
//    bottomOffset: TextUnit = 10.sp
//) = density.run {
//    val lineStart = box.left + (width.toPx() / 2)
//    val lineEnd = box.right - (width.toPx() / 2)
//    val lineBottom = box.bottom + bottomOffset.toPx()
//
//    val segmentWidth = wavelength.toPx() / SEGMENTS_PER_WAVELENGTH
//    val numOfPoints = ceil((lineEnd - lineStart) / segmentWidth).toInt() + 1
//
//    val animationProgress = rememberInfiniteTransition().animateFloat(
//        initialValue = 0f,
//        targetValue = 1f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(1.seconds.inWholeMilliseconds.toInt(), easing = LinearEasing),
//            repeatMode = RepeatMode.Restart
//        )
//    )
//    remember {
//        SquigglyUnderlineAnimator(animationProgress)
//    }
//
//    var pointX = lineStart
//    fastMapRange(0, numOfPoints) { point ->
//        val proportionOfWavelength = (pointX - lineStart) / wavelength.toPx()
//        val radiansX = proportionOfWavelength * TWO_PI + (TWO_PI * animator.animationProgress.value)
//        val offsetY = lineBottom + (sin(radiansX) * amplitude.toPx())
//
//        when (point) {
//            0 -> moveTo(pointX, offsetY)
//            else -> lineTo(pointX, offsetY)
//        }
//        pointX = (pointX + segmentWidth).coerceAtMost(lineEnd)
//    }
//}
//
//@OptIn(ExperimentalContracts::class)
//inline fun <R> fastMapRange(
//    start: Int,
//    end: Int,
//    transform: (Int) -> R
//): List<R> {
//    contract { callsInPlace(transform) }
//    val destination = ArrayList<R>(end - start + 1)
//    for (i in start..end) {
//        destination.add(transform(i))
//    }
//    return destination
//}