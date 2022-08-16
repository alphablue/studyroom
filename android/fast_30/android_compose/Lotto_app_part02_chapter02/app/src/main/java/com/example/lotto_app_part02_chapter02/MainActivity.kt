package com.example.lotto_app_part02_chapter02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeView()
        }
    }
}

@Composable
fun HomeView() {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            val items = lottoRandomNumbers()
            items.forEach {
                Text(text = it.toString())
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }
}

fun lottoRandomNumbers(): List<Int>{

    val numbers = mutableSetOf<Int>()

    while(numbers.size < 6) {
        val randomNumber = Random.nextInt(1..45)
        numbers.add(randomNumber)
    }

    return numbers.sorted()
}