package com.example.portfolio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import com.example.portfolio.ui.common.HardwareName
import com.example.portfolio.ui.common.PermissionName
import com.example.portfolio.ui.screen.util.observeAsState
import com.example.portfolio.ui.screen.util.permission.PermissionCheck
import com.example.portfolio.ui.theme.PortfolioTheme
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import com.example.portfolio.BuildConfig.GOOGLE_OAUTH
import com.example.portfolio.ui.screen.profile.RC_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var oAuth: FirebaseAuth

    private val activityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        oAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(GOOGLE_OAUTH)
            .requestEmail()
            .build()

        activityViewModel.authCheck = GoogleSignIn.getClient(this, gso)

        setContent {
            var permissionGrantCheck by remember { mutableStateOf(false) }

            PermissionCheck(
                permissionName = PermissionName.GPS,
                hardwareName = HardwareName.GPS,
                grantedCheck = { grant -> permissionGrantCheck = grant }
            )

            if (permissionGrantCheck) {

                when (LocalLifecycleOwner.current.lifecycle.observeAsState()) {
                    Lifecycle.Event.ON_START -> {
                        activityViewModel.startLocationUpdate()
                        Log.d("mapView", "ON_START start")
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        activityViewModel.startLocationUpdate()
                        Log.d("mapView", "onResume start")
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        activityViewModel.stopLocationUpdate()
                        Log.d("mapView", "onPause start")
                    }
                    else -> {}
                }

                PortfolioTheme {
                    StartApp(activityViewModel)
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if(task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: Exception) {
                    Log.d("signIn", "Google SignIn Failed")
                }
            } else {
                Log.d("SignIn", exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        oAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    Toast.makeText(this, "signIn Successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "signIn Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

data class User(
    val id: String,
    val profileImage: Uri,
    val name: String,
    val phoneNumber: String
)

data class Like(
    val id: String,
    val restaurantId: String,
    val restaurantImage: Uri,
    val restaurantName: String
)

data class Review(
    var takePicture: String = "",
    var rating: String = "",
    var content: String = "",
    var date: String = "",
    var userId: String = "",
    var restaurantId: String = "",
)

data class RestaurantMenu(
    var restaurantId: String? = "",
    var image: String? = "",
    var menuName: String? = "",
    var price: String? = "",
    var detailContent: String? = ""
)