package com.example.portfolio.di.modules

import com.example.portfolio.di.httpmodule.RetrofitSevices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object RetrofitModule {
    val GOOGLE_BASE_URL = "https://maps.googleapis.com/maps/api/geocode"

    @Provides
    fun provideRetrofitService(): RetrofitSevices{
        return Retrofit.Builder()
            .baseUrl(GOOGLE_BASE_URL)
            .build()
            .create(RetrofitSevices::class.java)
    }
}