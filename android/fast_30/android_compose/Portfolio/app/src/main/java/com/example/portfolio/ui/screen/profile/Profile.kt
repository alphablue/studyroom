package com.example.portfolio.ui.screen.profile

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import coil.compose.AsyncImage
import com.example.portfolio.MainActivity
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.R
import com.example.portfolio.ui.screen.util.findActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.android.material.internal.ContextUtils

const val RC_SIGN_IN = 1212

@Composable
fun Profile(
    modifier: Modifier,
    sharedViewModel: MainActivityViewModel
) {

    val context = LocalContext.current
    val activity = context.findActivity()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                signIn(activity, sharedViewModel)
            },
            modifier = Modifier
                .weight(0.8f)
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
}

private fun signIn(activity: Activity, sharedViewModel: MainActivityViewModel) {
    val signInIntent = sharedViewModel.authCheck?.signInIntent!!
    startActivityForResult(activity, signInIntent, RC_SIGN_IN, null)
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