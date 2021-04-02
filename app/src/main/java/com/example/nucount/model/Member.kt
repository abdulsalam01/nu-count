package com.example.nucount.model

import java.util.*

data class Member(val namaLengkap: String, val nik: String,
                  val statusNikah: String, val tanggalLahir: Date,
                  val tempatLahir: String, val jenisKelamin: String,
                  val nomor: String, val kabupaten: String, val kecamatan: String,
                  val desa: String, val dusun: String, val rt: String, val rw: String,
                  val pendidikan: String?, val pekerjaan: String?,
                  val subPekerjaan1: String?, val subPekerjaan2: String?,
                  val subPekerjaan3: String?, val penghasilan: String?,
                  val family: Family?) {
}