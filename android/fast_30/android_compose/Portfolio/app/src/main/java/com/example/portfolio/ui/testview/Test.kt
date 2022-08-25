package com.example.portfolio.ui.testview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun TestView() {
    var a by remember { mutableStateOf(10)}
    val b by remember { mutableStateOf(a)}

    Surface {
        Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = { a = Random.nextInt(1..100) }) {
                Text(text = "테스트용")
            }

            Text(text = "$b")
        }
    }
}