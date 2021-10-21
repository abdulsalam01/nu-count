package com.nu.nucount.model

import com.google.gson.annotations.SerializedName

data class Dusun(val id_dusun: String, val nama_dusun: String) {
    data class Response(@SerializedName("data")
                        val data: List<Dusun>,
                        @SerializedName("success")
                        val success: Boolean) {}

    override fun toString(): String {
        return nama_dusun
    }
}