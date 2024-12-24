package com.example.fastthirtyfivefinal.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fastthirtyfivefinal.ui.Main

fun NavGraphBuilder.mainSection() {
    composable<Main> {
        MainScreen()
    }
}


@Composable
fun MainScreen() {
    Column {
        Text(text = "main screen")
    }
}