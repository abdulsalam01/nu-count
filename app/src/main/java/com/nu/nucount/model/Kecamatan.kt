package com.nu.nucount.model

import com.google.gson.annotations.SerializedName

data class Kecamatan(val id_kecamatan: String, val nama_kecamatan: String) {
    data class Response(@SerializedName("data")
                        val data: List<Kecamatan>,
                        @SerializedName("success")
                        val success: Boolean) {}

    override fun toString(): String {
        return nama_kecamatan
    }
}