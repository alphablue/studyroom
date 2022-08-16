package com.example.bmi_app_part02_chapter01

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.text.DecimalFormat
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppNavHost()
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = startDestination
    ) {

       composable("home") {
           HomeView(navController)
       }
        composable(
            "result/{tt}",
            arguments = listOf(navArgument("tt") { type = NavType.FloatType})
        ) {
            val testArg = it.arguments?.getFloat("tt")
            val df = DecimalFormat("#.##")
            val convertData = df.format(testArg).toDouble()

            Log.d("test", "$testArg")

            testArg?.let {
                ResultView(result = convertData)
            }
        }
    }
}

@Composable
fun HomeView(navController: NavController) {
    var height by remember {
        mutableStateOf("")
    }

    var weight by remember { mutableStateOf("")}
    var result by remember { mutableStateOf(0.0)}

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "신장", fontWeight = FontWeight.Bold, fontSize = 26.sp)
            TextField(
                value = height,
                onValueChange = {height = it},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                label = {},
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .size(20.dp))
            Text(text = "체중", fontWeight = FontWeight.Bold, fontSize = 26.sp)
            TextField(
                value = weight,
                onValueChange = {weight = it},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                label = {},
            )

            Column(modifier = Modifier
                .fillMaxWidth()
                .height(40.dp), verticalArrangement = Arrangement.Center) {
                Button(onClick = {
                    result = weight.toDouble() /
                            (height.toDouble() / 100).pow(2)

                    Log.d("test", "return :: $result")
                    navController.navigate("result/$result")
                },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan),
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(text = "결과 보기")
                }
            }
        }
    }
}

@Composable
fun ResultView(result: Double) {
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        
        Column {
            val showText = when {
                result >= 35.0 -> "고도 비만"
                result >= 30.0 -> "중정도 비만"
                result >= 25.0 -> "경도 비만"
                result >= 23.0 -> "과체중"
                result >= 18.5 -> "정상체중"
                else -> "저체중"
            }
            Text(text = showText)
        }
    }
}

