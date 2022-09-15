package com.example.portfolio.di.httpmodule

import com.example.portfolio.BuildConfig.GOOGLE_MAPS_API_KEY
import com.example.portfolio.BuildConfig.TMAP_API_KEY
import com.example.portfolio.model.googlegeocode.GoogleGeoCode
import com.example.portfolio.model.tmap_poi.TmapPOIModel
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {

    @POST("{callType}?")
    fun getReverseGeoCode(
        @Path("callType") returnType: String,
        @Query("latlng") lng: String,
        @Query("language") language: String = "ko",
        @Query("key") key: String = GOOGLE_MAPS_API_KEY
    ): Call<GoogleGeoCode>

    @GET("/")
    fun getPOIWithTMAP(
        @Query("version") version: Int = 1,
        @Query("count") count: Int = 50,
        @Query("categories") searchCategory: String,
        @Query("centerLon") lng: Double,
        @Query("centerLat") lat: Double,
        @Query("radius") radius: Int = 1,
        @Query("appKey") key: String = TMAP_API_KEY
    ): Call<TmapPOIModel>
}