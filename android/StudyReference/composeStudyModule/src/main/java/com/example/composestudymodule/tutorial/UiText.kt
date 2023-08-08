package com.example.composestudymodule.tutorial

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toss.next.testapp.ui.theme.TutorialTheme

@Composable
fun UiText(name: String) {
//    Text(color = Color.Red, text = "Hello world")
//    Text(color = Color(0xffff9944), text = "Hello world")
//    Text(color = Color(0xffff9944), fontSize = 30.sp, text = "Hello world")
//    Text(color = Color(0xffff9944), fontWeight = FontWeight.Bold, fontSize = 30.sp, text = "Hello world")
//    Text(fontFamily = FontFamily.Cursive, color = Color(0xffff9944), fontWeight = FontWeight.Bold, fontSize = 30.sp, text = "Hello world")
//    Text(letterSpacing = 2.sp, fontFamily = FontFamily.Cursive, color = Color(0xffff9944), fontWeight = FontWeight.Bold, fontSize = 30.sp, text = "Hello world")
//    Text(maxLines = 2, letterSpacing = 2.sp, fontFamily = FontFamily.Cursive, color = Color(0xffff9944), fontWeight = FontWeight.Bold, fontSize = 30.sp, text = "Hello world\nHello world\nHello world")
//    Text(textDecoration = TextDecoration.Underline, maxLines = 2, letterSpacing = 2.sp, fontFamily = FontFamily.Cursive, color = Color(0xffff9944), fontWeight = FontWeight.Bold, fontSize = 30.sp, text = "Hello world\nHello world\nHello world")
    Text(modifier = Modifier.width(300.dp), textAlign = TextAlign.Center, textDecoration = TextDecoration.Underline, maxLines = 2, letterSpacing = 2.sp, fontFamily = FontFamily.Cursive, color = Color(0xffff9944), fontWeight = FontWeight.Bold, fontSize = 30.sp, text = "Hello world\nHello world\nHello world")
}

@Preview(showBackground = true)
@Composable
fun UiTextPreview() {
    TutorialTheme {
        UiText("샘플")
    }
}