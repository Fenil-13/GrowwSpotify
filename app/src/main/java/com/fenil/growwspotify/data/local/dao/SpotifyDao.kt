package com.fenil.growwspotify.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.fenil.growwspotify.data.local.entity.AlbumEntity
import com.fenil.growwspotify.data.local.entity.ArtistEntity
import com.fenil.growwspotify.data.local.entity.PlaylistEntity
import com.fenil.growwspotify.data.local.entity.TrackEntity

@Dao
interface SpotifyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAlbums(albums: List<AlbumEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArtists(artists: List<ArtistEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlaylists(playlists: List<PlaylistEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTracks(tracks: List<TrackEntity>)

    @Query("SELECT * FROM albums")
    suspend fun getAllAlbums(): List<AlbumEntity>?

    @Query("SELECT * FROM artists")
    suspend fun getAllArtists(): List<ArtistEntity>?

    @Query("SELECT * FROM playlists")
    suspend fun getAllPlaylists(): List<PlaylistEntity>?

    @Query("SELECT * FROM tracks")
    suspend fun getAllTracks(): List<TrackEntity>?

    @Query("DELETE FROM albums")
    suspend fun deleteAllAlbums()

    @Query("DELETE FROM artists")
    suspend fun deleteAllArtists()

    @Query("DELETE FROM playlists")
    suspend fun deleteAllPlaylists()

    @Query("DELETE FROM tracks")
    suspend fun deleteAllTracks()

    @Transaction
    suspend fun deleteAllData() {
        deleteAllAlbums()
        deleteAllArtists()
        deleteAllPlaylists()
        deleteAllTracks()
    }
}