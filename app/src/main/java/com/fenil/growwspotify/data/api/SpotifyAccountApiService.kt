package com.fenil.growwspotify.data.api

import com.fenil.growwspotify.data.model.AccessTokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SpotifyAccountApiService {

    @FormUrlEncoded
    @POST("api/token")
    fun getAccessToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Call<Response<AccessTokenResponse?>>
}