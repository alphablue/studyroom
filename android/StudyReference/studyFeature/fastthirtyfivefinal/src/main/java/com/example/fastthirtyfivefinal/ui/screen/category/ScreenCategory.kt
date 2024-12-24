package com.example.fastthirtyfivefinal.ui.screen.category

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.fastthirtyfivefinal.ui.Main

fun NavGraphBuilder.categorySection() {
    composable<Main> {
        CategoryScreen()
    }
}


@Composable
fun CategoryScreen() {
    Column {
        Text(text = "category screen")
    }
}