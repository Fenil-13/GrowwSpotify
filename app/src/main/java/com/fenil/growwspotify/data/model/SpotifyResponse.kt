package com.fenil.growwspotify.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyResponse(
    @SerializedName("albums")
    val albums: Albums?,
    @SerializedName("artists")
    val artists: Artists?,
    @SerializedName("playlists")
    val playlists: Playlists?,
    @SerializedName("tracks")
    val tracks: Tracks?
): Parcelable