package com.nu.pcnucount.model

import com.google.gson.annotations.SerializedName

data class Jabatan(val id_jabatan: String, val nama_jabatan: String) {
    data class Response(@SerializedName("data")
                        val data: List<Jabatan>,
                        @SerializedName("success")
                        val success: Boolean) {}

    override fun toString(): String {
        return nama_jabatan
    }
}