package com.example.fastthirtyfivefinal.di

import android.content.Context
import androidx.credentials.GetCredentialRequest
import com.example.fastthirtyfivefinal.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ThirtyFiveAppModule {

    @Singleton
    @Provides
    fun provideGoogleSignInRequester(
        @ApplicationContext context: Context
    ) : GetCredentialRequest {
        /**
         * 과거에 사용하던 방법
         * */
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .requestIdToken("small-size-apps.firebaseapp.com")
//            .requestId()
//            .requestProfile()
//            .build()
//        return GoogleSignIn.getClient(context, gso)


        /**
         * 24년 부터 제공되는 google 로그인의 새로운 방식
         * */
        val googleOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true) // 이전에 로그인하는데 사용된 계정이 있는지 확인 하는 옵션
            .setAutoSelectEnabled(true) // 기존 사용자의 자동 로그인 기능
            .setServerClientId(context.getString(R.string.firebase_auth_client_id))
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleOption)
            .build()
    }
}