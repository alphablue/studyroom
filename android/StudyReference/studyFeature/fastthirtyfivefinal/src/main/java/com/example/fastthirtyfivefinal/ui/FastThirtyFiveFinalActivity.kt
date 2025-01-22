package com.example.fastthirtyfivefinal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.fastthirtyfivefinal.ui.theme.StudyReferenceTheme
import com.example.fastthirtyfivefinal.viewmodel.TempViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * 프레젠테이션 레이어 이다.
 * */
@AndroidEntryPoint
class FastThirtyFiveFinalActivity : ComponentActivity() {

    private val viewModel: TempViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        enableEdgeToEdge()
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("test test test")
            }
        }
    }
}

@Composable
fun ShowOldVersion() {
    StudyReferenceTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MainScreenOlder()
        }
    }
}

@Composable
fun ShowNewVersion() {
    StudyReferenceTheme {

    }
}