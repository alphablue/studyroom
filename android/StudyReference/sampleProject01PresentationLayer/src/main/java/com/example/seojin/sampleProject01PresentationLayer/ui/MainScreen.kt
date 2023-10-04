package com.example.seojin.sampleProject01PresentationLayer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.seojin.sampleProject01PresentationLayer.ui.theme.Sample01Theme
import com.google.android.material.bottomnavigation.BottomNavigationItemView

@Composable
fun MainScreen() {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xffff0000),
                contentColor = Color(0xff00ff00)
            ){

            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {

        }
    }
}

@Composable
fun MainNavigationScreen() {

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Sample01Theme {
        MainScreen()
    }
}