package com.fenil.growwspotify.data.api

import com.fenil.growwspotify.data.model.SpotifyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpotifyApiService {

    @GET("v1/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SpotifyResponse
}