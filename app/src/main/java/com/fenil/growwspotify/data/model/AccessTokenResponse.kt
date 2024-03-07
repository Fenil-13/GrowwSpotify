package com.fenil.growwspotify.data.model

import com.google.gson.annotations.SerializedName

data class AccessTokenResponse(
    @SerializedName("access_token")
    val access_token: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Int,

)
