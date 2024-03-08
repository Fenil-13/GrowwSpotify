package com.fenil.growwspotify.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fenil.growwspotify.data.remote.model.SpotifyResponse
import com.fenil.growwspotify.data.remote.repo.SpotifyRepository
import com.fenil.growwspotify.utils.Resource
import com.fenil.growwspotify.utils.mergeItems
import com.fenil.growwspotify.utils.toSingleClickEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotifyViewModel @Inject constructor(
    private val repository: SpotifyRepository
) : ViewModel() {

    private var currentPage = 1

    val searchResultStateFlow = MutableStateFlow<SpotifyResponse?>(null)

    val clickEvent = MutableLiveData<Any>()
    val clickActionEventStream = clickEvent.toSingleClickEvent()

    private val _loadingState = MutableLiveData(false)
    val loadingState:LiveData<Boolean> = _loadingState

    private val _errorState = MutableLiveData("")
    val errorState:LiveData<String> = _errorState

    val offlineMode = MutableStateFlow(false)

    private var currentSearchQuery:String = ""

    fun searchQuery(query: String) {
        currentSearchQuery = query
        currentPage = 1
        search(query, currentPage)
    }

    fun fetchNextPage() {
        search(currentSearchQuery,currentPage)
    }

    private fun search(query: String, offset: Int) {
        viewModelScope.launch {
            _loadingState.value = offset == 1
            repository.search(query, offset)
                .collect { result ->
                    when(result){
                        is Resource.Loading -> {
                        }
                        is Resource.Failed -> {
                            _loadingState.value = false
                            _errorState.value = result.message
                        }
                        is Resource.Success -> {
                            offlineMode.value = false
                            val oldData = searchResultStateFlow.value
                            mergedData(oldData, result.data)
                            searchResultStateFlow.value = result.data
                            currentPage += 1
                            _loadingState.value = false
                        }
                        is Resource.NoInternet -> {
                            _loadingState.value = false
                            _errorState.value = result.message
                        }

                        is Resource.LocalSuccess ->{
                            offlineMode.value = true
                            val oldData = searchResultStateFlow.value
                            mergedData(oldData, result.data)
                            searchResultStateFlow.value = result.data
                            currentPage += 1
                            _loadingState.value = false
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