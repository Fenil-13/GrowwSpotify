package com.fenil.growwspotify.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fenil.growwspotify.data.remote.model.Image

@Entity(tableName = "artists")
data class ArtistEntity(
    @ColumnInfo("genres")
    val genres: String,
    @ColumnInfo("href")
    val href: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo("images")
    val images: String,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("type")
    val type: String,
    @ColumnInfo("uri")
    val uri: String
)