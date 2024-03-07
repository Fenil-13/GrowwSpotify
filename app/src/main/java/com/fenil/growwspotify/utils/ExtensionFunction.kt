package com.fenil.growwspotify.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun <T> mergeItems(
    newData: List<T>?,
    oldData: List<T>?
): List<T> {
    return (oldData ?: emptyList()) + (newData ?: emptyList())
}