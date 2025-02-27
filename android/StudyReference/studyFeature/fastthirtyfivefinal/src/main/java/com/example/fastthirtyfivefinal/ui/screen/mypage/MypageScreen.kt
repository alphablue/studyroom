package com.example.fastthirtyfivefinal.ui.screen.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.fastthirtyfivefinal.ui.MyPage

fun NavGraphBuilder.myPageSection() {
    composable<MyPage> {
        MyPageScreen()
    }
}


@Composable
fun MyPageScreen() {
    Column {
        Text(text = "MyPage screen")
    }
}