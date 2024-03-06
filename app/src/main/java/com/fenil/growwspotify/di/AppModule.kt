package com.fenil.growwspotify.di

import com.fenil.growwspotify.data.api.SpotifyApiService
import com.fenil.growwspotify.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideApiPostService(retrofit: Retrofit): SpotifyApiService =
        retrofit.create(SpotifyApiService::class.java)
}