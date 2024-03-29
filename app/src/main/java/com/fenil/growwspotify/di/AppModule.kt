package com.fenil.growwspotify.di

import android.content.Context
import androidx.room.Room
import com.fenil.growwspotify.data.local.AppDatabase
import com.fenil.growwspotify.data.local.dao.SpotifyDao
import com.fenil.growwspotify.data.remote.api.SpotifyAccountApiService
import com.fenil.growwspotify.data.remote.interceptor.AuthInterceptor
import com.fenil.growwspotify.data.remote.api.SpotifyApiService
import com.fenil.growwspotify.utils.Constants
import com.fenil.growwspotify.utils.Constants.ACCOUNT_BASE_URL
import com.fenil.growwspotify.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    @ApiRetrofitClient
    fun provideRetrofitBuilder(@AuthInterceptorHttpClient authHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(authHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideSpotifyDao(@ApplicationContext context: Context): SpotifyDao =
        Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
            .build()
            .spotifyDao()

    @Provides
    @Singleton
    @AccountApiRetrofitClient
    fun provideAccountRetrofitBuilder(): Retrofit =
        Retrofit.Builder()
            .baseUrl(ACCOUNT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @PlainHttpClient
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Singleton
    @Provides
    @AuthInterceptorHttpClient
    fun provideAuthInterceptorHttpClient(@PlainHttpClient okHttpClient: OkHttpClient, authInterceptor: AuthInterceptor): OkHttpClient {
        return okHttpClient.newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(@ApiRetrofitClient retrofit: Retrofit): SpotifyApiService =
        retrofit.create(SpotifyApiService::class.java)

    @Singleton
    @Provides
    fun provideAccountService(@AccountApiRetrofitClient retrofit: Retrofit): SpotifyAccountApiService =
        retrofit.create(SpotifyAccountApiService::class.java)
}