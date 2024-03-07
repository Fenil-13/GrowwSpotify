package com.fenil.growwspotify.data.repo

import com.fenil.growwspotify.data.api.SpotifyApiService
import com.fenil.growwspotify.data.model.SpotifyResponse
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
    private val networkHelper: NetworkHelper
) {

    private val PAGE_SIZE = 10

    suspend fun search(query: String, offset: Int): Flow<Resource<SpotifyResponse>> = flow {
        emit(Resource.loading())
        if (!networkHelper.isNetworkConnected()){
            emit(Resource.failed("No Internet Connection"))
            return@flow
        }
        val types = listOf("playlist", "track", "artist", "album")
        val result = apiService.search(query, types.joinToString(","), PAGE_SIZE,offset)
        emit(Resource.success(result))
    }.catch { exception ->
        emit(Resource.failed(exception.message.toString()))
    }.flowOn(Dispatchers.IO)
}