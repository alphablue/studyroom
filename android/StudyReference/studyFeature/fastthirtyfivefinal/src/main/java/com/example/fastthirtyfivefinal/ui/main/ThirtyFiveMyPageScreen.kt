package com.example.fastthirtyfivefinal.ui.main

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import com.example.fastthirtyfivefinal.viewmodel.MainViewModelOld
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ThirtyFiveMyPageScreen(
    viewModelOld: MainViewModelOld,
    googleSignInRequester: GetCredentialRequest,
    firebaseAuth: FirebaseAuth
) {
    val accountInfo by viewModelOld.accountInfo.collectAsState()
//    val firebaseAuth by lazy { FirebaseAuth.getInstance() } // 로그인, 아웃 기능을 사용하기 위한 인스턴스 (과거)
    val context = LocalContext.current
    val activityContext = LocalActivity.current?.baseContext
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)
    var credentialResult: GetCredentialResponse? = null

//    val startForResult = rememberLauncherForActivityResult( // startForActivityResult 의 compose 버전
//        contract = ActivityResultContracts.StartActivityForResult()
//    ) { result: ActivityResult ->
//        if(result.resultCode == Activity.RESULT_OK) {
//            val indent = result.data
// djjallaflnflsadlc contecsadf
//            if(indent != null) {
//                val task: Task<GetCredentialRequest> =
//            }
//        }
//    }

    // 파이어베이스 로그인 관련 코드들
    LaunchedEffect(accountInfo) {
        coroutineScope.launch {
            if (activityContext != null) {
                try {
                    credentialResult = credentialManager
                        .getCredential(
                            context = activityContext,
                            request = googleSignInRequester
                        )

                    // 이후에 이벤트 실행 하도록 정의 할 것
                    // 아래에서 각 상황별로 handling 하는 것에 대해서 보여주고 있다.
                    val credential = credentialResult!!.credential

                    // ??
                    when (credential) {

                        // passKey
                        is PublicKeyCredential -> {
                            // response Json
                            credential.authenticationResponseJson
                        }

                        // Password
                        is PasswordCredential -> {
                            val userName = credential.id
                            val password = credential.password
                        }

                        // googleIdToken
                        is CustomCredential -> {
                            handleSignIn(firebaseAuth, credentialResult!!.credential)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    // 새로운 로그인 방법
    val currentUser = firebaseAuth.currentUser
    if (currentUser != null) {
        // google id 토큰을 받음 -> firebase 증명 으로 교환 -> firebase 증명을 활용해 로그인

    }

    // 로그인 ui
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        // 로그인 된 상태
        if (currentUser != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "로그인 유저 : ${accountInfo?.name}",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {
                        viewModelOld.signOutGoogle()
                        signOut(firebaseAuth, credentialManager, coroutineScope)
                    }
                ) {
                    Text(text = "로그아웃")
                }
            }
        } else {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    credentialResult?.let {
                        handleSignIn(firebaseAuth, it.credential)
                    }
                }
            ) {
                Text(text = "로그인")
            }
        }
    }
}

/**
 * 파이어 베이스의 설정을 하는 것은 별개로 ui 설정
 * */
private fun handleSignIn(
    firebaseAuth: FirebaseAuth,
    credential: Credential
) {
    if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
        val idToken = GoogleIdTokenCredential.createFrom(credential.data).idToken

        firebaseAuthWithGoogle(firebaseAuth, idToken)
    }
}

private fun firebaseAuthWithGoogle(
    firebaseAuth: FirebaseAuth,
    idToken: String
) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    firebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) { // 성공
                val user = firebaseAuth.currentUser

                // ui 작업
            } else { // 실패
                // ui 작업
            }
        }
}

private fun signOut(
    firebaseAuth: FirebaseAuth,
    credentialManager: CredentialManager,
    coroutineScope: CoroutineScope
) {
    firebaseAuth.signOut()

    // 유저가 로그아웃을 하면 인증 관련 정보를 제거해 줘야함
    coroutineScope.launch {
        try {
            val clearRequest = ClearCredentialStateRequest()

            credentialManager.clearCredentialState(clearRequest)

            // ui 작업
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}