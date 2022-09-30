package com.example.portfolio.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.ui.theme.textColor

@Composable
fun LoginPage(
    sharedViewModel: MainActivityViewModel,
    upPress: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .wrapContentSize()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }

        LoginPageTopBar(upPress, "")

        Column {
            TextField(value = email, onValueChange = { email = it })
            TextField(value = pass, onValueChange = { pass = it })
        }

        if (sharedViewModel.loginState.not()) {
            Button(
                onClick = {
                    sharedViewModel.signInWithEmailPassword(email, pass)
                }
            ) {
                Text(text = "로그인")
            }

            Button(
                onClick = {
                    sharedViewModel.signUpEmailPass(
                        email, pass
                    )
                }
            ) {
                Text("가입하기")
            }
        }

        /**
         * TODO
         * 구글 연동 로그인 기능 버튼 추가
         * */
//        GoogleSignButton(sharedViewModel = sharedViewModel)

    }
}

@Composable
fun LoginPageTopBar(
    upPress: () -> Unit,
    title: String,
) {
    TopAppBar(modifier = Modifier
        .statusBarsPadding()
        .fillMaxWidth()) {
        IconButton(onClick = upPress, modifier = Modifier.align(Alignment.Top)) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                tint = textColor,
                contentDescription = "back"
            )
        }

        Text(
            title,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}