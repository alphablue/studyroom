package com.example.fastthirtyfivefinal.ui.screen.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.fastthirtyfivefinal.ui.Main

fun NavGraphBuilder.myPageSection() {
    composable<Main> {
        MyPageScreen()
    }
}


@Composable
fun MyPageScreen() {
    Column {
        Text(text = "MyPage screen")
    }
}