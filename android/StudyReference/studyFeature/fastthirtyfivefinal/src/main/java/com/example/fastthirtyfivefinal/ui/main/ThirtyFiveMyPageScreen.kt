package com.example.fastthirtyfivefinal.ui.main

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import com.example.fastthirtyfive_domain.model.ThirtyFiveAccountInfo
import com.example.fastthirtyfivefinal.util.d
import com.example.fastthirtyfivefinal.util.i
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
    firebaseAuth: FirebaseAuth,
    activityContext: Context
) {
    val accountInfo by viewModelOld.accountInfo.collectAsState()
//    val firebaseAuth by lazy { FirebaseAuth.getInstance() } // 로그인, 아웃 기능을 사용하기 위한 인스턴스 (과거)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)
    val currentUser = firebaseAuth.currentUser // state 로 사용이 된다. 값이 바뀔때마다 데이터가 넘어옴

    if(accountInfo != null) {
        "로그인 정보 존재".d()
    } else {
        "로그인 정보 XXXXX".d()
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
                        signOut(firebaseAuth, credentialManager, coroutineScope)
                        viewModelOld.signOutGoogle()
                    }
                ) {
                    Text(text = "로그아웃")
                }
            }
        } else {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    handleSignIn(firebaseAuth, credentialManager, googleSignInRequester, viewModelOld, activityContext, coroutineScope)
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
    credentialManager: CredentialManager,
    googleSignInRequester: GetCredentialRequest,
    viewModelOld: MainViewModelOld,
    activityContext: Context,
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch {
        credentialManager.getCredential(
            activityContext,  // 무조건 activity context 를 가져와야 한다.
            googleSignInRequester
        ).credential.let {
            when (it) {
                // passKey
                is PublicKeyCredential -> {
                    // response Json
                    it.authenticationResponseJson
                }
                // Password
                is PasswordCredential -> {
                    val userName = it.id
                    val password = it.password
                }
                // googleIdToken
                is CustomCredential -> {
                    if (it.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val idToken = GoogleIdTokenCredential.createFrom(it.data).idToken

                        firebaseAuthWithGoogle(viewModelOld, firebaseAuth, idToken)
                    }
                }
            }
        }
    }
}

private fun firebaseAuthWithGoogle(
    mainViewModelOld: MainViewModelOld,
    firebaseAuth: FirebaseAuth,
    idToken: String
) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    firebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) { // 성공
                val user = firebaseAuth.currentUser

                // ui 작업
                "로그인 성공".i()
                mainViewModelOld.signInGoogle(
                    ThirtyFiveAccountInfo(
                        idToken,
                        user?.displayName ?: "최초사용자",
                        ThirtyFiveAccountInfo.LoginType.GOOGLE
                    )
                )
            } else { // 실패
                // ui 작업
                "로그인 실패".i()
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