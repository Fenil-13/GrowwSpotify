package com.fenil.growwspotify.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fenil.growwspotify.data.local.dao.SpotifyDao
import com.fenil.growwspotify.data.local.entity.AlbumEntity
import com.fenil.growwspotify.data.local.entity.ArtistEntity
import com.fenil.growwspotify.data.local.entity.PlaylistEntity
import com.fenil.growwspotify.data.local.entity.TrackEntity
import com.fenil.growwspotify.data.remote.model.Image

@Database(entities = [AlbumEntity::class, TrackEntity::class, PlaylistEntity::class, ArtistEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun spotifyDao(): SpotifyDao
}