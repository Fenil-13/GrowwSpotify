package com.fenil.growwspotify.utils

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData

fun Context.showToast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun <T> mergeItems(
    newData: List<T>?,
    oldData: List<T>?
): List<T> {
    return (oldData ?: emptyList()) + (newData ?: emptyList())
}

fun <T> LiveData<T>.toSingleClickEvent(): LiveData<T> {
    val result = LiveEvent<T>()
    result.addSource(this) {
        result.value = it
    }
    return result
}
