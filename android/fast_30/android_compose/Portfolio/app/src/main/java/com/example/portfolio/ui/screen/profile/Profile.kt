package com.example.portfolio.ui.screen.profile

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.portfolio.MainActivityViewModel

@Composable
fun Profile(
    modifier: Modifier,
    sharedViewModel: MainActivityViewModel
) {

    val context = LocalContext.current
    val auth = sharedViewModel.auth

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }


        Column {
            TextField(value = email, onValueChange = { email = it })
            TextField(value = pass, onValueChange = { pass = it })
        }

        Button(
            onClick = {
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        ) {
            Text("가입하기")
        }

        Button(
            onClick = {
                auth.signOut()
            }
        ) {
            Text(text = "로그아웃")
        }
    /**
     * TODO
     * 구글 연동 로그인 기능 버튼 추가
     * */
//        GoogleSignButton(sharedViewModel = sharedViewModel)

    }
}

@Composable
fun UserBox(
    modifier: Modifier = Modifier,
    userImg: Uri,
    userName: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .weight(0.35f)
                .fillMaxSize(),
            model = userImg,
            contentDescription = "userProfileImage"
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(12.dp)
        )
        Text(
            text = "$userName 님",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}