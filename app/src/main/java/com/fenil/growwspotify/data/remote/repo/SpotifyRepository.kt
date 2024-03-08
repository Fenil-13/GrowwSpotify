package com.fenil.growwspotify.data.remote.repo

import com.fenil.growwspotify.data.local.dao.SpotifyDao
import com.fenil.growwspotify.data.local.entity.AlbumEntity
import com.fenil.growwspotify.data.local.entity.ArtistEntity
import com.fenil.growwspotify.data.local.entity.PlaylistEntity
import com.fenil.growwspotify.data.local.entity.TrackEntity
import com.fenil.growwspotify.data.remote.api.SpotifyApiService
import com.fenil.growwspotify.data.remote.model.AlbumItem
import com.fenil.growwspotify.data.remote.model.Albums
import com.fenil.growwspotify.data.remote.model.ArtistItem
import com.fenil.growwspotify.data.remote.model.Artists
import com.fenil.growwspotify.data.remote.model.Image
import com.fenil.growwspotify.data.remote.model.PlaylistItem
import com.fenil.growwspotify.data.remote.model.Playlists
import com.fenil.growwspotify.data.remote.model.SpotifyResponse
import com.fenil.growwspotify.data.remote.model.TrackItem
import com.fenil.growwspotify.data.remote.model.Tracks
import com.fenil.growwspotify.utils.NetworkHelper
import com.fenil.growwspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SpotifyRepository @Inject constructor(
    private val apiService: SpotifyApiService,
    private val spotifyDao: SpotifyDao,
    private val networkHelper: NetworkHelper
) {

    private val PAGE_SIZE = 10

    suspend fun search(query: String, offset: Int): Flow<Resource<SpotifyResponse>> = flow {
        emit(Resource.loading())
        if (!networkHelper.isNetworkConnected()){
            // Fetch data from Room Database when offline
            if (offset!=1) return@flow
            val result = getLocalData()
            if (result != null) {
                emit(Resource.successWithLocal(result))
            }else{
                emit(Resource.noInternet("No Internet Connection"))
            }
            return@flow
        }
        val types = listOf("playlist", "track", "artist", "album")
        val result = apiService.search(query, types.joinToString(","), PAGE_SIZE,offset)
        emit(Resource.success(result))
        //save data to local
        saveDataToLocal(result)
    }.catch { exception ->
        emit(Resource.failed(exception.message.toString()))
    }.flowOn(Dispatchers.IO)

    private suspend fun getLocalData(): SpotifyResponse? {
        val albumsItem = spotifyDao.getAllAlbums()?.map {
            AlbumItem(
                null,
                it.href,
                it.id,
                listOf(Image(null,it.image,null)),
                it.name,
                it.releaseDate,
                it.totalTracks,
                it.type,
                it.uri
            )
        }
        val album = Albums(null, albumsItem,null,null,null,null,null)

        val artistsItem = spotifyDao.getAllArtists()?.map {
            ArtistItem(
                null,
                listOf(it.genres ?: ""),
                it.href,
                it.id,
                listOf(Image(null,it.images,null)),
                it.name,
                it.type,
                it.uri
            )
        }
        val artist = Artists(null, artistsItem,null,null,null,null,null)

        val playlistsItem = spotifyDao.getAllPlaylists()?.map {
            PlaylistItem(
                description = it.description,
                href = it.href,
                id = it.id,
                images = listOf(Image(null,it.images,null)), // Assuming you have a property named "image" in PlaylistItem for a single image
                name = it.name,
                type = it.type,
                uri = it.uri,
                owner = null,
                tracks = null
            )
        }
        val playlist = Playlists(null, playlistsItem,null,null,null,null,null)

        val tracksItem = spotifyDao.getAllTracks()?.map {
            TrackItem(
                album = null,
                artists = null,
                discNumber = it.discNumber,
                durationMs = it.durationMs,
                explicit = it.explicit,
                href = it.href,
                id = it.id,
                isLocal = it.isLocal,
                name = it.name,
                popularity = it.popularity,
                previewUrl = it.previewUrl,
                trackNumber = it.trackNumber,
                type = it.type,
                uri = it.uri,
                thumbnail = it.thumbnail
            )
        }
        val track = Tracks(null, tracksItem,null,null,null,null,null)

        if (albumsItem.isNullOrEmpty() || artistsItem.isNullOrEmpty() || playlistsItem.isNullOrEmpty() || tracksItem.isNullOrEmpty()) {
            return null
        }
        return SpotifyResponse(album, artist, playlist, track)
    }

    private suspend fun saveDataToLocal(result: SpotifyResponse) {
        //Clear existing data
        spotifyDao.deleteAllData()
        val albums = result.albums?.items?.map {
            AlbumEntity(
                it.href.orEmpty(),
                it.id.orEmpty(),
                it.images?.firstOrNull()?.url.orEmpty(),
                it.name.orEmpty(),
                it.releaseDate.orEmpty(),
                it.totalTracks ?: 0,
                it.type.orEmpty(),
                it.uri.orEmpty()
            )
        }
        val playlists = result.playlists?.items?.map {
            PlaylistEntity(
                it.description.orEmpty(),
                it.href.orEmpty(),
                it.id.orEmpty(),
                it.images?.firstOrNull()?.url.orEmpty(),
                it.name.orEmpty(),
                it.type.orEmpty(),
                it.uri.orEmpty()
            )
        }
        val tracks = result.tracks?.items?.map {
            TrackEntity(
                it.discNumber ?: 0,
                it.durationMs ?: 0,
                it.explicit ?: false,
                it.href.orEmpty(),
                it.id.orEmpty(),
                it.isLocal ?: false,
                it.name.orEmpty(),
                it.popularity ?: 0,
                it.previewUrl.orEmpty(),
                it.trackNumber ?: 0,
                it.type.orEmpty(),
                it.uri.orEmpty(),
                it.album?.images?.firstOrNull()?.url.orEmpty()
            )
        }
        val artists = result.artists?.items?.map {
            ArtistEntity(
                it.genres?.firstOrNull().orEmpty(),
                it.href.orEmpty(),
                it.id.orEmpty(),
                it.images?.firstOrNull()?.url.orEmpty(),
                it.name.orEmpty(),
                it.type.orEmpty(),
                it.uri.orEmpty()
            )
        }
        spotifyDao.insertAllAlbums(albums ?: listOf())
        spotifyDao.insertAllPlaylists(playlists ?: listOf())
        spotifyDao.insertAllTracks(tracks ?: listOf())
        spotifyDao.insertAllArtists(artists ?: listOf())
    }
}