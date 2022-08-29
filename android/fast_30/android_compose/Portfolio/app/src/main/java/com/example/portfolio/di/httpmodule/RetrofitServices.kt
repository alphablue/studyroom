package com.example.portfolio.di.httpmodule

import com.example.portfolio.BuildConfig.GOOGLE_MAPS_API_KEY
import com.example.portfolio.model.googlegeocode.GoogleGeoCode
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RetrofitServices {

    @POST("{callType}?")
    fun getReverseGeoCode(
        @Path("callType") returnType: String,
        @Query("latlng") lng: String,
        @Query("language") language: String = "ko",
        @Query("key") key: String = GOOGLE_MAPS_API_KEY
    ): Call<GoogleGeoCode>
}