package com.fenil.growwspotify.data.remote.interceptor

import android.content.Context
import com.fenil.growwspotify.data.remote.api.SpotifyAccountApiService
import com.fenil.growwspotify.utils.AppPreferences
import com.fenil.growwspotify.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    @ApplicationContext val context: Context,
    private val spotifyAccountApiService: SpotifyAccountApiService
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        fetchNewOrUpdatedTokens(context, chain)
        val requestWithHeaders = originalRequest.newBuilder().apply {
            header("Content-Type", "application/json")
            header("Authorization","Bearer ${AppPreferences(context).getAuthToken()}")
        }
        return chain.proceed(requestWithHeaders.build())
    }

    private fun fetchNewOrUpdatedTokens(context: Context, chain: Interceptor.Chain) {
        val appPreferences = AppPreferences(context)
        if (appPreferences.hasTokenExpired()) {
            val response = chain.proceed(
                spotifyAccountApiService.getAccessToken(
                    grantType = Constants.GRANT_TYPE,
                    clientId = Constants.CLIENT_ID,
                    clientSecret = Constants.CLIENT_SECRET
                ).request()
            )
            response.let {
                if (it.isSuccessful){
                    val responseObject = JSONObject(it.body()?.string() ?: "")
                    val accessToken = responseObject.getString("access_token")
                    val expireTime = responseObject.getInt("expires_in")
                    appPreferences.saveAuthTokenWithCurrentTime(accessToken,expireTime)
                }
            }
        }
    }
}