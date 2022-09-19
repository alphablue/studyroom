package com.example.portfolio.ui.screen.home

import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.portfolio.model.tmap_poi.Poi
import com.example.portfolio.repository.TMapRepository
import com.example.portfolio.viewmodel.BaseViewModel
import com.example.portfolio.viewmodel.DispatcherProvider
import com.example.portfolio.viewmodel.onIO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

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
        count: Int = 100
    ) = onIO {
        val lat = location.latitude
        val lng = location.longitude

        Log.d("HomeViewModel", "getPoi called")

        try {
            _poiList.clear()
            Log.d("HomeViewModel", "getPoi show location data : ${location.toString()}")

            category?.let { category ->
                val resultList = tMapRepository
                    .getPOIWithTMAP(lat, lng, category, count)
                    .searchPoiInfo
                    .pois
                    .poi


                _poiList.addAll(convertPoiData(resultList))

            }?: run {
                val resultList = tMapRepository
                    .getPOIWithTMAP(lat, lng,count = count)
                    .searchPoiInfo
                    .pois
                    .poi

                _poiList.addAll(convertPoiData(resultList))
            }
        } catch (e: Exception) {
            Log.d("HomeViewModel", "getPoi exception ${e.message}")
        }
    }

    private fun convertPoiData(poiData: List<Poi>): MutableList<NearRestaurantInfo> {
        val result = mutableListOf<NearRestaurantInfo>()

        poiData.forEach { modelItem ->
            val address = "${modelItem.upperAddrName} ${modelItem.roadName} ${modelItem.buildingNo1} ${modelItem.buildingNo2}"
            val name = modelItem.name
            val modelLat = modelItem.frontLat.toDouble()
            val modelLon = modelItem.frontLon.toDouble()

            result.add(NearRestaurantInfo(name, address, lon=modelLon, lat=modelLat))
        }

        return result
    }
}

data class NearRestaurantInfo(
    val name: String,
    val address: String,
    val lon: Double,
    val lat: Double,
    val rating: Float = Random.nextDouble(0.0, 5.0).toFloat(),
    val deliveryTime: String = run {
        val firstTime = Random.nextInt(1..7)
        val secondTime = Random.nextInt(10..35)

        "${firstTime}분 ~ ${secondTime}분"
    },
    val deliveryTip: String = run {
        val firstTip = Random.nextInt(100..350)
        val secondTip = Random.nextInt(500..4000)

        "배달팁 ${firstTip}원 ~ ${secondTip}원"
    }
)