package com.example.portfolio.ui.screen.home

import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.portfolio.di.repository.TMapRepository
import com.example.portfolio.model.tmap_poi.Poi
import com.example.portfolio.model.uidatamodels.NearRestaurantInfo
import com.example.portfolio.viewmodel.BaseViewModel
import com.example.portfolio.viewmodel.DispatcherProvider
import com.example.portfolio.viewmodel.onIO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tMapRepository: TMapRepository,
    dispatcherProvider: DispatcherProvider,
) : BaseViewModel(dispatcherProvider) {

    private val _poiList = mutableStateListOf<NearRestaurantInfo>()
    var poiList = _poiList

    fun getPoiData(
        location: Location,
        searchCategory: String?,
        count: Int = 100
    ) = onIO {
        val lat = location.latitude
        val lng = location.longitude

        Log.d("HomeViewModel", "getPoi called, $searchCategory")

        try {
            _poiList.clear()
            Log.d("HomeViewModel", "getPoi show location data : $location")

            searchCategory?.let { category ->
                val resultList = tMapRepository
                    .getPOIWithTMAP(lat, lng, category, count)
                    .searchPoiInfo
                    .pois
                    .poi

                _poiList.addAll(convertPoiData(resultList))

            } ?: run {
                val resultList = tMapRepository
                    .getPOIWithTMAP(lat, lng, count = count)
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
            val address =
                "${modelItem.upperAddrName} ${modelItem.roadName} ${modelItem.buildingNo1} ${modelItem.buildingNo2}"
            val name = modelItem.name
            val modelLat = modelItem.frontLat.toDouble()
            val modelLon = modelItem.frontLon.toDouble()

            result.add(
                NearRestaurantInfo(
                    modelItem.id,
                    imgUri = null,
                    name = name,
                    address = address,
                    lon = modelLon,
                    lat = modelLat
                )
            )
        }

        return result
    }
}