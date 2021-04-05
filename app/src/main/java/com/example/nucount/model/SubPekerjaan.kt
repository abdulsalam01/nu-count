package com.example.nucount.model

import com.google.gson.annotations.SerializedName

data class SubPekerjaan1(val id_sub: String, val nama_sub: String, val id_tb_pekerjaan: String) {
    data class Response(@SerializedName("data")
                        val data: List<SubPekerjaan1>,
                        @SerializedName("success")
                        val success: Boolean) {}

    override fun toString(): String {
        return nama_sub
    }
}

data class SubPekerjaan2(val id_sub2: String, val nama_sub2: String, val id_tb_sub1: String) {
    data class Response(@SerializedName("data")
                        val data: List<SubPekerjaan2>,
                        @SerializedName("success")
                        val success: Boolean) {}

    override fun toString(): String {
        return nama_sub2
    }
}