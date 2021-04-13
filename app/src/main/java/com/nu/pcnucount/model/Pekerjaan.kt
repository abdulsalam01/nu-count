package com.nu.pcnucount.model

import com.google.gson.annotations.SerializedName

data class Pekerjaan(val id_pekerjaan: String, val nama_pekerjaan: String) {
    data class Response(@SerializedName("data")
                        val data: List<Pekerjaan>,
                        @SerializedName("success")
                        val success: Boolean) {}

    override fun toString(): String {
        return nama_pekerjaan
    }
}