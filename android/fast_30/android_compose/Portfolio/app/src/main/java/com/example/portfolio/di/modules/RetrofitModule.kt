package com.example.portfolio.di.modules

import com.example.portfolio.di.httpmodule.RetrofitServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val GOOGLE_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideRetrofitService(okHttpClient: OkHttpClient): Retrofit =
         Retrofit.Builder()
            .baseUrl(GOOGLE_BASE_URL)
             .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideRetrofitServiceClient(retrofit: Retrofit): RetrofitServices = retrofit.create(RetrofitServices::class.java)
}