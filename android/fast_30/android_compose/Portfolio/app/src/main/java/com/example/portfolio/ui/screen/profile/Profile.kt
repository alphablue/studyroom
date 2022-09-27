package com.example.portfolio.ui.screen.profile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import coil.compose.AsyncImage
import com.example.portfolio.BuildConfig.GOOGLE_OAUTH
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.R
import com.example.portfolio.ui.screen.util.findActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

const val RC_SIGN_IN = 1212

@Composable
fun Profile(
    modifier: Modifier,
    sharedViewModel: MainActivityViewModel
) {

    val context = LocalContext.current
    val activity = context.findActivity()
    val auth = Firebase.auth

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            Log.d("SignIn", "activity not result ok")
            // The user cancelled the login, was it due to an Exception?
            if (result.data?.action == ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST) {
                val exception: Exception? = result.data?.getSerializableExtra(
                    ActivityResultContracts.StartIntentSenderForResult.EXTRA_SEND_INTENT_EXCEPTION) as Exception?
                Log.d("SignIn", "Couldn't start One Tap UI: ${exception?.localizedMessage}")
            }

            return@rememberLauncherForActivityResult
        }
        val oneTapClient = Identity.getSignInClient(context)
        val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
        val idToken = credential.googleIdToken
        when {
            idToken != null -> {
                Log.d("SignIn", "login success")
            }
            else -> Log.d("SignIn", "Erro sem token")
        }
    }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("")}
        var pass by remember { mutableStateOf("")}


        Column {
            TextField(value = email, onValueChange = {email = it})
            TextField(value = pass, onValueChange = { pass = it})
        }

        Button(
            onClick = {
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener{ task ->
                    if(task.isSuccessful) {
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

        val scope = rememberCoroutineScope()
        Button(
            onClick = {
                scope.launch {
                    signIn(context, launcher)
                }
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

suspend fun signIn(
    context: Context,
    launcher: ActivityResultLauncher<IntentSenderRequest>
) {
    val oneTapClient = Identity.getSignInClient(context)
    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(GOOGLE_OAUTH)
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    try{
        val result = oneTapClient.beginSignIn(signInRequest).await()
        val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent).build()

        Log.d("SignIn", "sign In ok")
        launcher.launch(intentSenderRequest)
    } catch (e: Exception) {
        Log.d("SignIn", e.message.toString())
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