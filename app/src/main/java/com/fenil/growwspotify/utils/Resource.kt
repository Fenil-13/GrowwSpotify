package com.fenil.growwspotify.utils

sealed class Resource<T> {

    class Loading<T> : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class LocalSuccess<T>(val data: T) : Resource<T>()
    data class Failed<T>(val message: String) : Resource<T>()
    data class NoInternet<T>(val message: String) : Resource<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> successWithLocal(data: T) = LocalSuccess(data)
        fun <T> failed(message: String) = Failed<T>(message)
        fun <T> noInternet(message: String) = NoInternet<T>(message)
    }
}