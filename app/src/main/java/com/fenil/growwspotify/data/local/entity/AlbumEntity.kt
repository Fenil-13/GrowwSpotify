package com.fenil.growwspotify.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fenil.growwspotify.data.remote.model.ArtistItem
import com.fenil.growwspotify.data.remote.model.Image

@Entity(tableName = "albums")
class AlbumEntity(
    @ColumnInfo("href")
    val href: String,
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo("images")
    val image: String,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("release_date")
    val releaseDate: String,
    @ColumnInfo("total_tracks")
    val totalTracks: Int,
    @ColumnInfo("type")
    val type: String,
    @ColumnInfo("uri")
    val uri: String
)