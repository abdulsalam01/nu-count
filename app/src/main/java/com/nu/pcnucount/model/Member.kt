package com.nu.pcnucount.model

data class Member(
    val id: Int,
    val namaLengkap: String, val nik: String,
    val statusNikah: String, val tanggalLahir: String?,
    val tempatLahir: String, val jenisKelamin: String,
    val nomor: String, val kabupaten: String, val kecamatan: String,
    val desa: String, val dusun: String, val rt: String, val rw: String,
    val pendidikan: String?,
    val keterangan: String?,
    val pekerjaan: String?,
    val subPekerjaan1: String?, val subPekerjaan2: String?,
    val subPekerjaan3: String?, val penghasilan: String?,
    val anggota: String,
    val katanu: String, val struktur: String,
    val jenisStruktur: String, val jabatan: String,
    val periode: String,
    val idPetugas: Int,
    val family: List<Family>?) {
}