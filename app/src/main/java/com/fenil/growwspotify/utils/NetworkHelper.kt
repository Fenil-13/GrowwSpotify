package com.fenil.growwspotify.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkHelper @Inject constructor(@ApplicationContext private val context: Context) {

    fun isNetworkConnected() = isNetworkConnected(context)

    private fun isNetworkConnected(context: Context?): Boolean {
        if (context == null) {
            return false
        }

        val manager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
        return manager?.run {
            val isMobile = getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.isConnectedOrConnecting == true
            val isWifi = getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnectedOrConnecting == true
            isMobile || isWifi
        } ?: false
    }
}
