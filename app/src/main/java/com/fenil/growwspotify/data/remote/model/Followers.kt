package com.fenil.growwspotify.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Followers(
    @SerializedName("total")
    val total: Int?
): Parcelable