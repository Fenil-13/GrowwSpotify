package com.fenil.growwspotify.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    @SerializedName("display_name")
    val displayName: String?,
    @SerializedName("external_urls")
    val externalUrls: HashMap<String,String>?,
    @SerializedName("href")
    val href: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("uri")
    val uri: String?
): Parcelable