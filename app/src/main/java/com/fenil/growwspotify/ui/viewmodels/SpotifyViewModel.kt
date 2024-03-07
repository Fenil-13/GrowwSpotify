package com.fenil.growwspotify.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fenil.growwspotify.data.model.SpotifyResponse
import com.fenil.growwspotify.data.repo.SpotifyRepository
import com.fenil.growwspotify.utils.Resource
import com.fenil.growwspotify.utils.mergeItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotifyViewModel @Inject constructor(
    private val repository: SpotifyRepository
) : ViewModel() {

    private var currentPage = 1

    val searchResultStateFlow = MutableStateFlow<SpotifyResponse?>(null)
    val navigateToDetailsPageTrigger = MutableStateFlow<Any?>(null)

    val loadingState = MutableStateFlow(false)
    val errorState = MutableStateFlow("")

    private var currentSearchQuery:String = ""

    fun searchQuery(query: String) {
        currentSearchQuery = query
        search(query, currentPage)
    }

    fun fetchNextPage() {
        search(currentSearchQuery,currentPage)
    }

    private fun search(query: String, offset: Int) {
        viewModelScope.launch {
            loadingState.value = true
            repository.search(query, offset)
                .collect { result ->
                    when(result){
                        is Resource.Loading -> {
                            loadingState.value = currentPage == 0
                        }
                        is Resource.Failed -> {
                            loadingState.value = false
                            errorState.value = result.message
                        }
                        is Resource.Success -> {
                            val oldData = searchResultStateFlow.value
                            mergedData(oldData, result.data)
                            searchResultStateFlow.value = result.data
                            currentPage += 1
                            loadingState.value = false
                        }
                    }
                }
        }
    }

    private fun mergedData(oldData: SpotifyResponse?, data: SpotifyResponse?) {
        data?.artists?.items = mergeItems(data?.artists?.items, oldData?.artists?.items)
        data?.playlists?.items = mergeItems(data?.playlists?.items, oldData?.playlists?.items)
        data?.tracks?.items = mergeItems(data?.tracks?.items, oldData?.tracks?.items)
        data?.albums?.items = mergeItems(data?.albums?.items, oldData?.albums?.items)
    }

    fun clearList() {
        currentSearchQuery = ""
        currentPage = 1
        searchResultStateFlow.value = null
    }
}