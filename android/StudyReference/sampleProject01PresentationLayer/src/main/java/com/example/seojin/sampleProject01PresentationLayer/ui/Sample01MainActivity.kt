package com.example.seojin.sampleProject01PresentationLayer.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.seojin.sampleProject01PresentationLayer.ui.theme.Sample01Theme
import com.example.seojin.sampleProject01PresentationLayer.viewmodel.TempViewModel

class Sample01MainActivity: ComponentActivity() {

    private val viewModel : TempViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Sample01Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}

