package com.example.portfolio.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.portfolio.model.googlegeocode.GoogleGeoCode
import com.example.portfolio.repository.GoogleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val googleRepository: GoogleRepository,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    var geocodeState by mutableStateOf<GoogleGeoCode?>(null)

    fun getData(
        returnType: String,
        lat: Double,
        lng: Double
    ) = onIO {
        try{
            geocodeState = googleRepository.getReverseGeoCodeData(returnType, lat, lng)
        } catch (e: Exception) {
            Log.d("Exception", "TestViewModel:: ${e.message}")
        }
    }
}