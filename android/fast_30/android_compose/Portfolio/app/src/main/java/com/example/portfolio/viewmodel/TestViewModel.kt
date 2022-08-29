package com.example.portfolio.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.portfolio.model.googlegeocode.GoogleGeoCode
import com.example.portfolio.repository.GoogleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val googleRepository: GoogleRepository,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private var geocodeState by mutableStateOf<GoogleGeoCode?>(null)

    fun getData(
        returnType: String,
        lat: Double,
        lng: Double
    ) = onIO {
        try{
            val testData = googleRepository.getReverseGeoCodeData(returnType, lat, lng)
            geocodeState = testData
            Log.d("TestViewModel", "get data :: ${testData.results}")
        } catch (e: Exception) {
            Log.d("TestViewModel_Exception", "TestViewModel:: ${e.message}")
        }
    }
}