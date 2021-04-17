package com.nu.pcnucount.model

import com.google.gson.annotations.SerializedName

data class Struktur(val id_struktur: String, val nama_struktur: String) {
    data class Response(@SerializedName("data")
                        val data: List<Struktur>,
                        @SerializedName("success")
                        val success: Boolean) {}

    override fun toString(): String {
        return nama_struktur
    }
}