package com.example.nucount.core.constant

object API {
    const val isOffline = true

    val BASE_URL = if (isOffline) "local/" else "network/"
    val PREFIX = "api/"
}