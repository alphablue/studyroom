package com.example.portfolio.ui.screen.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.R
import kotlinx.coroutines.launch

@Composable
fun GoogleSignButton(
    sharedViewModel: MainActivityViewModel,
    modifier: Modifier = Modifier
) {
/**
 * TODO
 * 구글 로그인 연동하는 기능을 차후에는 적용 할 것
 * */
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                Log.d("testSignIn", "google sign in button clicked")
            }
        },
        modifier = modifier
            .wrapContentSize(Alignment.Center)
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_googlelogo),
            contentDescription = "googleIcon"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "구글로 로그인 하기")
    }
}