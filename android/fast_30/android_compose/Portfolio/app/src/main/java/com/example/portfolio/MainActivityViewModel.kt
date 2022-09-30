package com.example.portfolio

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.portfolio.model.googlegeocode.GoogleGeoCode
import com.example.portfolio.repository.GoogleRepository
import com.example.portfolio.repository.firebasemodule.FirebaseObject
import com.example.portfolio.ui.screen.home.NearRestaurantInfo
import com.example.portfolio.viewmodel.BaseViewModel
import com.example.portfolio.viewmodel.DispatcherProvider
import com.example.portfolio.viewmodel.onIO
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val googleRepository: GoogleRepository,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val defaultLocationRequestClient = LocationRequest.create().apply {
        priority = Priority.PRIORITY_HIGH_ACCURACY
        interval = 5000
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            _realTimeUserLocation = result.lastLocation
        }
    }

    var splitAddress by mutableStateOf("위치 정보를 찾을 수 없습니다.")
    var userSettingLocation: Location? = null

    // 위도 경도 데이터를 관찰하는 부분
    private var _realTimeUserLocation by mutableStateOf<Location?>(null)
    val realTimeUserLocation: Location?
        get() = _realTimeUserLocation

    // detail 부분에서 데이터를 받기위한 부분
    var detailItem by mutableStateOf<NearRestaurantInfo?>(null)

    // 로그인을 위한 객체생성
    val auth = FirebaseAuth.getInstance()
    var loginState by mutableStateOf(false)
    var userInfo: User? by mutableStateOf(null)

    // 테스트를 위한 것
    private val packageName = context.packageName

    @SuppressLint("MissingPermission")
    fun startLocationUpdate(
        locationRequestClient: LocationRequest = defaultLocationRequestClient,
        locationCallback: LocationCallback = this.locationCallback,
    ) {
        fusedLocationClient.requestLocationUpdates(
            locationRequestClient,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    fun getLocation(callback: (location: Location) -> Unit) {
        fusedLocationClient.lastLocation.addOnCompleteListener {
            callback(it.result)
        }
        Log.d("mainActivityViewModel", "getLocation Call")
    }

    fun getReverseGeoCode(
        returnType: String = "json",
        lat: Double,
        lng: Double,
        callback: (GoogleGeoCode) -> Unit
    ) = onIO {
        try {
            googleRepository.getReverseGeoCodeData(returnType, lat, lng).let {
                callback(it)
            }
        } catch (e: Exception) {
            Log.d("MainActivityViewModel", "reverseGeoCode error : ${e.message}")
        }
    }

    fun reverseGeoCodeCallBack(lastLocation: Location) =
        lastLocation.let {
            getReverseGeoCode(lat = it.latitude, lng = it.longitude) { geoCode ->
                splitAddress = googleGeoCodeConvert(geoCode)
            }
        }

    fun googleGeoCodeConvert(geoCode: GoogleGeoCode): String =
        geoCode.results.first()
            .formattedAddress
            .split(" ")
            .filterIndexed { index, _ ->
                index > 1
            }
            .joinToString(" ")

    fun signUpEmailPass(
        email: String,
        pass: String,
    ) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("testSign", "회원가입 완료")

                    val userID = task.result?.user?.uid.toString()
                    FirebaseObject.addUserId(
                        userID,
                        User(
                            id = userID,
                            profileImage = "android.resource://$packageName/${R.drawable.test_user_02}",
                            name = "로그인 유저 테스트",
                            phoneNumber = "010-3434-2321"
                        )
                    )

                    checkLoginState()

                } else {
                    Log.d("testSign", "회원가입 실패")
                }
            }
    }

    fun checkLoginState() {
        loginState = auth.currentUser != null

        if (loginState) {
            auth.currentUser?.let {
                FirebaseObject.getUser(it.uid) { userData ->
                    userInfo = userData
                }
            }
        } else userInfo = null
    }

    fun signInWithEmailPassword(
        email: String,
        password: String
    ) {
        auth.signInWithEmailAndPassword(
            email, password
        )
            .addOnSuccessListener {
                Log.d("testSign", "로그인 성공")
                checkLoginState()
            }
            .addOnFailureListener { e ->
                Log.d("testSign", "로그인 실패 ${e.message}")
            }
    }

    fun userWithdrawal() {
        val user = auth.currentUser

        val uid = user?.uid
        uid?.let {
            FirebaseObject.deleteUserId(it)
            user.delete()
                .addOnCompleteListener {
                    Log.d("testSign", "회원탈퇴 완료")
                    checkLoginState()
                }
                .addOnFailureListener {
                    Log.d("testSign", "회원탈퇴 실패")
                }
        }
    }

    fun signOut() {
        auth.signOut()
        checkLoginState()
    }
}