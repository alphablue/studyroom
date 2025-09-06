package com.example.studystartingpoint.challengeUi.veriantEffect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.studystartingpoint.ui.theme.Black40
import com.example.studystartingpoint.ui.theme.White

@Composable
fun ShimmerComposeContainer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
    ) {  }
}

@Composable
fun ShimmerComposeBase() {
    val colorStop = arrayOf(
        0.4f to Black40,
        1f to White
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.horizontalGradient(colorStops = colorStop))
    ) {

    }
}