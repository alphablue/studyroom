package com.example.studystartingpoint.challengeUi.text

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.studystartingpoint.ui.theme.Purple
import com.example.studystartingpoint.ui.theme.StudyReferenceTheme

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