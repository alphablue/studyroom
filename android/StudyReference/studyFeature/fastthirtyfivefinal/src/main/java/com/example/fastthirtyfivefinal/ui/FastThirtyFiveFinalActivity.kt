package com.example.fastthirtyfivefinal.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        Toast.makeText(this, "TempValue = ${viewModel.getTempModel().name}", Toast.LENGTH_SHORT).show()

        enableEdgeToEdge()
        setContent {
            StudyReferenceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudyReferenceTheme {
        Greeting("Android")
    }
}