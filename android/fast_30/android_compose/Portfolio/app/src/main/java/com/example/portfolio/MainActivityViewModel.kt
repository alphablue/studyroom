package com.example.portfolio

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.portfolio.model.googlegeocode.GoogleGeoCode
import com.example.portfolio.repository.GoogleRepository
import com.example.portfolio.ui.screen.home.NearRestaurantInfo
import com.example.portfolio.viewmodel.BaseViewModel
import com.example.portfolio.viewmodel.DispatcherProvider
import com.example.portfolio.viewmodel.onIO
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.location.*
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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

    // 구글 로그인 작업 부분
    val loadingState = MutableStateFlow(LoadingState.IDLE)
    val auth = FirebaseAuth.getInstance()

    // google sign in options
    private val gso = GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.web_client_id))
        .requestEmail()
        .requestProfile()
        .requestId()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    fun initCheck() {
        Log.d("testSignIn", "SharedViewModel init :: $gso, $googleSignInClient")
    }

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

    fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful.not()) {
                        Log.d("testSignIn", "로그인 실패")
                    } else {
                        val idToken = auth.currentUser?.getIdToken(true)
                            ?.addOnCompleteListener { tokenTask ->
                                if(tokenTask.isSuccessful) {
                                    val token = tokenTask.result.token
                                    Log.d("testSignIn", "토큰 획득 :: $token")
                                }
                            }
                        Log.d("testSignIn", "로그인 성공:: $idToken")
                    }
                }
                .addOnFailureListener { e->
                    Log.d("testSignIn", "credential 실패 : ${e.message}")
                }
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun googleSignOut() {
        googleSignInClient.signOut()
    }
}

data class LoadingState constructor(
    val status: Status,
    val msg: String? = null
) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val IDLE = LoadingState(Status.IDLE)
        val LOADING = LoadingState(Status.RUNNING)
        val LOGGED_IN = LoadingState(Status.LOGGED_IN)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        IDLE,
        LOGGED_IN
    }
}