package com.example.studystartingpoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.studystartingpoint.challengeUi.text.customViewCheck
import com.example.studystartingpoint.ui.theme.Purple
import com.example.studystartingpoint.ui.theme.StudyReferenceTheme
import com.example.studystartingpoint.util.d

class ActivityStartingPoint : ComponentActivity() {
    @OptIn(ExperimentalTextApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var onDraw: DrawScope.() -> Unit by remember { mutableStateOf({}) }

            StudyReferenceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .customViewCheck(),
                                color = Color.Yellow,
                                text = "월드 월드 hello hello hello hello hello hello hello hello"
                            )

                            val textAnnotation = buildAnnotatedString {
                                append("텍스트 입니다.")
                                withAnnotation("태그", annotation = "어노테이션") {
                                    withStyle(SpanStyle(color = Purple)) {
                                        append("냠냠냠 맛있다.") // 8글자
                                    }
                                }
                            }

                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .drawBehind { onDraw() },
                                text = textAnnotation,
                                onTextLayout = { textLayout ->

                                    // stringBuilder 에서 annotation을 정의한 값을 가져온다. 만들어진 글자의 index 중 start 와 end 사이에 tag에 해당하는 내용이 있다면 추출된다. 없다면 크래시
                                    val annotation = textAnnotation.getStringAnnotations("태그", 0, 999).let {
                                        it.forEach { list ->
                                            list.toString().d("customText")
                                        }

                                        it.first()
                                    }

                                    val textBounds = mutableListOf<Rect>()

                                    textAnnotation.indexOf("냠냠냠 맛있다.").let {
                                        for (idx in it until it + 8) {
                                            val boundBox = textLayout.getBoundingBox(idx)
                                            textBounds.add(boundBox)
                                            "text bounds : $boundBox".d("customText")
                                        }
                                    }

                                    onDraw = {
                                        for (bound in textBounds) {
                                            val underline = bound.copy(top = bound.bottom - 4.sp.toPx())
                                            drawRect(
                                                color = Purple,
                                                topLeft = underline.topLeft,
                                                size = underline.size
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}