package com.example.studystartingpoint.challengeUi.text

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.studystartingpoint.ui.theme.Purple
import com.example.studystartingpoint.ui.theme.StudyReferenceTheme
import com.example.studystartingpoint.util.d

@OptIn(ExperimentalTextApi::class)
@Composable
fun UnderLineWave() {
    val textContainer = buildAnnotatedString {
        append("I'll be alright as long as there's light from a ")
        withAnnotation("squiggles", annotation = "ignored") {
            withStyle(SpanStyle(color = Purple)) {
                append("neon moon")
            }
        }
    }

    var onDraw: DrawScope.() -> Unit by remember { mutableStateOf({}) }

    Text(
        modifier = Modifier.drawBehind { onDraw },
        text = textContainer,
        onTextLayout = { layoutResult ->
            val annotation = textContainer.getStringAnnotations("squiggles", 0, 4)
            val textBounds = layoutResult.getBoundingBox(3)

            onDraw = {
            }
        }
    )
}

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = layout { measurable, constraints ->
    // Measure the composable
    val placeable = measurable.measure(constraints)

    // Check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]

    // Height of the composable with padding - first baseline
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    layout(placeable.width, height) {
        // Where the composable gets placed
        placeable.placeRelative(0, placeableY)
    }
}

fun Modifier.customViewCheck() = layout { measurable, constraints ->

    "constraints 의 크기 >> width : ${constraints.maxWidth}, height : ${constraints.maxHeight},".d("customView") // px 단위로 부모로 부터 받은 크기를 나타낸다.

    val placeable = measurable.measure(constraints)
    "place 의 크기 확인 height : ${placeable.height}, width : ${placeable.width}, measureHeight : ${placeable.measuredHeight}, measureWidth : ${placeable.measuredWidth}".d("customView")
    "firstBaseLine : $FirstBaseline [${placeable[FirstBaseline]}] , lastBaseLine : $LastBaseline [${placeable[LastBaseline]}], ".d("customView")


    layout(placeable.width, placeable.height) {
        placeable.placeRelative(0, 0)
    }
}

@Preview
@Composable
fun TextViewCustom1() {
    StudyReferenceTheme {
        Text(modifier = Modifier.firstBaselineToTop(12.dp), text = "헬로 월드")
    }
}

@Preview
@Composable
fun TextViewCustom2() {
    StudyReferenceTheme {
        Text(modifier = Modifier.customViewCheck(), text = "헬로 월드")
    }
}

@Preview
@Composable
fun TextStyleCheck() {
    val text = buildAnnotatedString {
        append("Hello")
        pushStyle(SpanStyle(Color.Green))

        append("World")
        pop()

        append("!")
        addStyle(SpanStyle(Color.Red), "Hello World".length, this.length)

        append("Hello World")
        toAnnotatedString()
    }

    StudyReferenceTheme {
        Text(
            modifier = Modifier.background(Color.White),
            text = text
        )
    }
}