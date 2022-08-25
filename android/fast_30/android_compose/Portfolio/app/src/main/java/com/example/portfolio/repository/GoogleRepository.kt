package com.example.portfolio.repository

import com.example.portfolio.di.httpmodule.RetrofitServices
import com.example.portfolio.model.googlegeocode.GoogleGeoCode
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
import kotlin.coroutines.suspendCoroutine

@Module
@InstallIn(SingletonComponent::class)
class GoogleRepository @Inject constructor(
    private val retrofit: RetrofitServices
) {

    suspend fun getReverseGeoCodeData(
        returnType: String,
        lat: Double,
        lng: Double
    ): GoogleGeoCode = withContext(Dispatchers.IO) {
        suspendCoroutine { supCoroutine ->
            retrofit.getReverseGeoCode(returnType, lat, lng)
                .enqueue(object : Callback<GoogleGeoCode> {
                    override fun onResponse(
                        call: Call<GoogleGeoCode>,
                        response: Response<GoogleGeoCode>
                    ) {
                        val body = response.body()


                        body?.let {
                            supCoroutine.resume(it)
                        }
                    }

                    override fun onFailure(call: Call<GoogleGeoCode>, t: Throwable) {
                        throw Exception("error")
                    }
                })
        }
    }
}