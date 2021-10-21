package com.nu.nucount.model

import com.google.gson.annotations.SerializedName

data class Desa(val id_desa: String, val nama_desa: String) {
    data class Response(@SerializedName("data")
                        val data: List<Desa>,
                        @SerializedName("success")
                        val success: Boolean) {}

    override fun toString(): String {
        return nama_desa
    }
}