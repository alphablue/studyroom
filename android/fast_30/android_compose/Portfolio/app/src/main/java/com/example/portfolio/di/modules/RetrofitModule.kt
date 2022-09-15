package com.example.portfolio.di.modules

import com.example.portfolio.di.httpmodule.RetrofitServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GoogleRetrofitService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TMapRetrofitService


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val GOOGLE_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/"
    private const val TMAP_BASE_URL = "https://apis.openapi.sk.com/tmap/pois/search/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    @GoogleRetrofitService
    fun provideGoogleRetrofitService(okHttpClient: OkHttpClient): RetrofitServices =
        Retrofit.Builder()
            .baseUrl(GOOGLE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitServices::class.java)

    @Singleton
    @Provides
    @TMapRetrofitService
    fun provideTMapRetrofitService(okHttpClient: OkHttpClient): RetrofitServices =
        Retrofit.Builder()
            .baseUrl(TMAP_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitServices::class.java)
}