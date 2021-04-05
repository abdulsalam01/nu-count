package com.example.nucount.core.constant

import com.example.nucount.model.*
import retrofit2.Call
import retrofit2.http.*

interface Service {

    @FormUrlEncoded
    @POST(API.LOGIN)
    fun login(@Field("username")
              username: String,
              @Field("password")
              password: String) : Call<User.Response>

    @GET(API.GET_DESA)
    fun getDesa(): Call<Desa.Response>

    @GET(API.GET_DUSUN)
    fun getDusun(): Call<Dusun.Response>

    @GET(API.GET_KECAMATAN)
    fun getKecamatan(): Call<Kecamatan.Response>

    @GET(API.GET_RT)
    fun getRt(): Call<Rt.Response>

    @GET(API.GET_RW)
    fun getRw(): Call<Rw.Response>

    @GET(API.GET_PEKERJAAN)
    fun getPekerjaan(): Call<Pekerjaan.Response>

    @GET(API.GET_SUB_1 + "{id}")
    fun getSubPekerjaan1(@Path("id") id: Int): Call<SubPekerjaan1.Response>

    @GET(API.GET_SUB_2 + "{id}")
    fun getSubPekerjaan2(@Path("id") id: Int): Call<SubPekerjaan2.Response>

}