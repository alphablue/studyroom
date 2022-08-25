package com.example.portfolio.di.httpmodule

import com.example.portfolio.BuildConfig.GOOGLE_MAPS_API_KEY
import com.example.portfolio.model.googlegeocode.GoogleGeoCode
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitServices {

    @POST("/{callType}?latlng={lat},{lng}&language={language}&key=$GOOGLE_MAPS_API_KEY")
    fun getReverseGeoCode(
        @Path("callType") returnType: String,
        @Path("lat") lat: Double,
        @Path("lng") lng: Double,
        @Path("language") language: String = "ko"
    ): Call<GoogleGeoCode>
}