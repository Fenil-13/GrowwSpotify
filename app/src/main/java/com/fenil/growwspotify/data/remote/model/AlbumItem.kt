package com.fenil.growwspotify.data.remote.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumItem(
    @SerializedName("artists")
    val artists: List<ArtistItem>?,
    @SerializedName("href")
    val href: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("images")
    val images: List<Image?>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("total_tracks")
    val totalTracks: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("uri")
    val uri: String?
): Parcelable