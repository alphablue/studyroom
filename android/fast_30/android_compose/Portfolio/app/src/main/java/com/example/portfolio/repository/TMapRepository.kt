package com.example.portfolio.repository

import android.util.Log
import com.example.portfolio.di.httpmodule.RetrofitServices
import com.example.portfolio.model.tmap_poi.TmapPOIModel
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Module
@InstallIn(SingletonComponent::class)
class TMapRepository @Inject constructor(
    private val retrofit: RetrofitServices
){

    suspend fun getPOIWithTMAP(
        lat: Double,
        lng: Double,
        category: String = "식당",
        count: Int = 20,
    ): TmapPOIModel = withContext(Dispatchers.IO) {
        suspendCoroutine { supCoroutine ->
            retrofit.getPOIWithTMAP(
                lat = lat,
                lng = lng,
                searchCategory = category,
                count = count
            ).enqueue(object: Callback<TmapPOIModel> {
                override fun onResponse(
                    call: Call<TmapPOIModel>,
                    response: Response<TmapPOIModel>
                ) {
                    val body = response.body()

                    body?.let {
                        supCoroutine.resume(it)
                    } ?: Log.d("TMapRepository", "poi body is empty")
                }

                override fun onFailure(call: Call<TmapPOIModel>, t: Throwable) {
                    Log.d("TMapRepository", "TMapRepository Error")
                    throw Exception("TMapRepository Error")
                }
            })
        }
    }
}