package com.fenil.growwspotify.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Playlists(
    @SerializedName("href")
    val href: String?,
    @SerializedName("items")
    var items: List<PlaylistItem>?,
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("total")
    val total: Int?
): Parcelable