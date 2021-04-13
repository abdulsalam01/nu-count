package com.nu.pcnucount.model

import com.google.gson.annotations.SerializedName

data class Rt(val id_rt: String, val nama_rt: String) {
    data class Response(@SerializedName("data")
                        val data: List<Rt>,
                        @SerializedName("success")
                        val success: Boolean) {}

    override fun toString(): String {
        return nama_rt
    }
}