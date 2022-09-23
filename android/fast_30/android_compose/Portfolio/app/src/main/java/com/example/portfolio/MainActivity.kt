package com.example.portfolio

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import com.example.portfolio.repository.firebasemodule.FirebaseObject
import com.example.portfolio.ui.common.HardwareName
import com.example.portfolio.ui.common.PermissionName
import com.example.portfolio.ui.screen.util.observeAsState
import com.example.portfolio.ui.screen.util.permission.PermissionCheck
import com.example.portfolio.ui.theme.PortfolioTheme
import com.google.type.DateTime
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val activityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var permissionGrantCheck by remember{ mutableStateOf(false)}

            PermissionCheck(
                permissionName = PermissionName.GPS,
                hardwareName = HardwareName.GPS,
                grantedCheck = { grant -> permissionGrantCheck = grant}
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
    val takePicture: String,
    val rating: String,
    val content: String,
    val date: String,
    val userId: String,
    val restaurantId: String,
)

data class RestaurantMenu(
    val restaurantId: String,
    val imageUri: String,
    val menuName: String,
    val price: String,
    val detailContent: String
)