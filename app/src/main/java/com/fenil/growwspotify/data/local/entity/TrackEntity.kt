package com.fenil.growwspotify.data.local.entity


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tracks")
data class TrackEntity(
    @SerializedName("disc_number")
    val discNumber: Int,
    @SerializedName("duration_ms")
    val durationMs: Int,
    @SerializedName("explicit")
    val explicit: Boolean,
    @SerializedName("href")
    val href: String,
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("is_local")
    val isLocal: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("popularity")
    val popularity: Int,
    @SerializedName("preview_url")
    val previewUrl: String,
    @SerializedName("track_number")
    val trackNumber: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String,
    @SerializedName("thumbnail")
    val thumbnail: String
): Parcelable