package com.fenil.growwspotify.data.api

import com.fenil.growwspotify.data.model.SpotifyResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface SpotifyApiService {

    @Headers("Authorization: Bearer BQCTmK8pm8tfOfUQwPlwo0S4O8x0tnm12cCPUiv-uuFoxCmxKlKg_aOXweXFJTwiB0BtQXabpyZyBxI83JcUvv3kqnPpR2PQnt2rQA-eIrqj_aMqEwU")
    @GET("v1/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SpotifyResponse
}