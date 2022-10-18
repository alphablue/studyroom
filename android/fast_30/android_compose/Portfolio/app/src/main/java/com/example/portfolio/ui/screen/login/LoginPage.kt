package com.example.portfolio.ui.screen.login

import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.ui.common.SimpleTitleTopBar
import com.example.portfolio.ui.screen.util.TextUtil
import com.example.portfolio.ui.theme.secondaryBlue

@Composable
fun LoginPage(
    sharedViewModel: MainActivityViewModel,
    upPress: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(Color.White)
            .wrapContentSize()
            .fillMaxSize()
    ) {
        var email by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }

        SimpleTitleTopBar(upPress, "로그인")

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailField(email = email, onEmailChange = {email = it})
            Spacer(modifier = Modifier.height(5.dp))
            PasswordField(password = pass, onPassWordChange = { pass = it})

            if (sharedViewModel.loginState.not()) {
                Button(
                    onClick = {
                        sharedViewModel.signInWithEmailPassword(
                            email, pass,
                            successCallback = upPress,
                            failCallback = {
                                Toast.makeText(context,
                                    "이메일과 비밀번호를 확인해 주세요.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                ) {
                    Text(text = "로그인")
                }

                Button(
                    onClick = {
                        sharedViewModel.signUpEmailPass(
                            email, pass,
                            successCallback = {
                                Toast.makeText(context, "회원가입이 완료 되었습니다.", Toast.LENGTH_SHORT).show()
                                upPress()
                            },
                            failCallback = {
                                Toast.makeText(context, "회원가입 실패 다시시도 해 주세요.", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                ) {
                    Text("가입하기")
                }
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
fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit
) {
    var emailValidCheck by remember { mutableStateOf(true) }

    TextField(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        value = email,
        onValueChange = {
            onEmailChange(it.replace(" ", ""))
                        },
        label = { Text("Email") },
        placeholder = { Text("example@gmail.com") },
        singleLine = true,
        trailingIcon = {
            if (email.isBlank().not())
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear text",
                    modifier = Modifier
                        .size(12.dp)
                        .clickable {
                            onEmailChange("")
                        }
                )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {
            emailValidCheck = TextUtil.isValidEmail(email)
        })
    )

    if (emailValidCheck.not()) {
        Text(text = "이메일을 확인해 주세요.", color = MaterialTheme.colors.error)
    }
}

@Composable
fun PasswordField(
    password: String,
    onPassWordChange: (String) -> Unit,
) {
    var isValidPassword by remember { mutableStateOf(true) }

    TextField(
        value = password,
        singleLine = true,
        placeholder = { Text(text = "영문자, 숫자 조합 6자리 이상 13자리 이하", fontSize = 10.sp) },
        onValueChange = { onPassWordChange(it.replace(" ", "")) },
        modifier = Modifier
            .fillMaxWidth(0.8f),
        label = { Text(text = "password") },
        visualTransformation = PasswordVisualTransformation(),
        trailingIcon = {
            if (password.isBlank().not())
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear text",
                    modifier = Modifier
                        .size(12.dp)
                        .clickable {
                            onPassWordChange("")
                        }
                )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                Log.d("password", "${TextUtil.isValidPassword(password)}, $password")
                isValidPassword = TextUtil.isValidPassword(password)

                if (isValidPassword.not()) {
                    onPassWordChange("")
                }
            }
        )
    )

    if (isValidPassword.not())
        Text(
            text = "비밀번호는 영문 소문자, 숫자를 혼합하여 6자리 이상 13자리 이하여야 합니다.",
            color = MaterialTheme.colors.error
        )
}