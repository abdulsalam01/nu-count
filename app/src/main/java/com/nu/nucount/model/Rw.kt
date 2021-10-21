package com.nu.nucount.model
import com.google.gson.annotations.SerializedName

data class Rw(val id_rw: String, val nama_rw: String) {
    data class Response(@SerializedName("data")
                        val data: List<Rw>,
                        @SerializedName("success")
                        val success: Boolean) {}

    override fun toString(): String {
        return nama_rw
    }
}