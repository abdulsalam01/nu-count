package com.nu.nucount.core.helper

import android.content.Context
import android.net.ConnectivityManager

object ConnectionChecker {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo = connectionManager.activeNetworkInfo

        return internetInfo != null && internetInfo.isConnected
    }
}