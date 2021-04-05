package com.example.nucount.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id_user")
    val id_user: Int,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("level")
    val level: Int,
    @SerializedName("tb_tugas")
    val tb_tugas: String
) {
    data class Response(@SerializedName("data")
                        val data: User,
                        @SerializedName("success")
                        val success: Boolean) {}
}