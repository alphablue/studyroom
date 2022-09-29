package com.example.portfolio.ui.screen.profile

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.portfolio.BuildConfig.GOOGLE_OAUTH
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.R
import com.example.portfolio.ui.screen.util.findActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun GoogleSignButton(
    sharedViewModel: MainActivityViewModel,
    modifier: Modifier = Modifier
) {
    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {

                Log.d("testSignIn", "activity result")

                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)

                Log.d("testSignIn", "${task.result}")
                try {
                    Log.d("testSignIn", "로그인 시도 launcher")
                    val account = task.getResult(ApiException::class.java)!!
                    val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                    sharedViewModel.signWithCredential(credential)

                } catch (e: ApiException) {
                    Log.d("testSignIn", "Google sign in fail $e")
                }
        }

    val signClient = sharedViewModel.googleSignInClient
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                Log.d("testSignIn", "google sign in button clicked")
                launcher.launch(signClient.signInIntent)
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