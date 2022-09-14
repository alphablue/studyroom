package com.example.portfolio.ui.screen.home

import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.portfolio.repository.TMapRepository
import com.example.portfolio.viewmodel.BaseViewModel
import com.example.portfolio.viewmodel.DispatcherProvider
import com.example.portfolio.viewmodel.onIO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tMapRepository: TMapRepository,
    dispatcherProvider: DispatcherProvider,
): BaseViewModel(dispatcherProvider) {

    private val _poiList = mutableStateListOf<NearRestaurantInfo>()
    var poiList = _poiList

    fun getPoiData(
        location: Location,
        category: String?,
        count: Int = 200
    ) = onIO {
        val lat = location.latitude
        val lng = location.longitude

        try {
            category?.let { category ->
                val resultList = tMapRepository
                    .getPOIWithTMAP(lat, lng, category, count)
                    .searchPoiInfo
                    .pois
                    .poi

                resultList.forEach { modelItem ->
                    val address = "${modelItem.upperAddrName} ${modelItem.roadName} ${modelItem.buildingNo1} ${modelItem.buildingNo2}"
                    val name = modelItem.name
                    val modelLat = modelItem.frontLat.toDouble()
                    val modelLon = modelItem.frontLon.toDouble()

                    _poiList.add(NearRestaurantInfo(name, address, lon=modelLon, lat=modelLat))
                }

            }?: tMapRepository.getPOIWithTMAP(lat, lng, count = count)
        } catch (e: Exception) {
            Log.d("HomeViewModel", "getPoi exception ${e.message}")
        }
    }
}

data class NearRestaurantInfo(
    val name: String,
    val address: String,
    val lon: Double,
    val lat: Double,
    val rating: Double = Random.nextDouble(0.0, 5.0)
)