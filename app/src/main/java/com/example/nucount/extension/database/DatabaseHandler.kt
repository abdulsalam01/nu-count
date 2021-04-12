package com.example.nucount.extension.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

abstract class DatabaseHandler(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "local_input"
    }

    // general-case
    protected val ID = "id"

    protected val TABLE_MEMBER = "member"
    protected val TABLE_FAMILY = "family"

    protected val NAMA_LENGKAP = "nama_lengkap"
    protected val NIK = "nik"
    protected val STATUS_NIKAH = "status_nikah"
    protected val TANGGAL_LAHIR = "tanggal_lahir"
    protected val TEMPAT_LAHIR = "tempat_lahir"
    protected val JENIS_KELAMIN = "jenis_kelamin"
    protected val NOMOR = "nomor"
    protected val KABUPATEN = "kabupaten"
    protected val KECAMATAN = "kecamatan"
    protected val DESA = "desa"
    protected val DUSUN = "dusun"
    protected val RT = "rt"
    protected val RW = "rw"
    protected val PENDIDIKAN = "pendidikan"
    protected val PEKERJAAN = "pekerjaan"
    protected val SUB_PEKERJAAN_1 = "sub_pekerjaan_1"
    protected val SUB_PEKERJAAN_2 = "sub_pekerjaan_2"
    protected val SUB_PEKERJAAN_3 = "sub_pekerjaan_3"
    protected val PENGHASILAN = "penghasilan"
    protected val ANGGOTA = "anggota"
    protected val ID_PETUGAS = "id_petugas"

    protected val NAMA = "nama"
    protected val USIA = "usia"
    protected val HK = "hk"

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_MEMBER_TABLE = ("CREATE TABLE $TABLE_MEMBER (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NAMA_LENGKAP TEXT," +
                "$NIK TEXT," +
                "$STATUS_NIKAH TEXT," +
                "$TANGGAL_LAHIR TEXT," +
                "$TEMPAT_LAHIR TEXT," +
                "$JENIS_KELAMIN TEXT," +
                "$NOMOR TEXT," +
                "$KABUPATEN TEXT," +
                "$KECAMATAN TEXT," +
                "$DESA TEXT," +
                "$DUSUN TEXT," +
                "$RT TEXT," +
                "$RW TEXT," +
                "$PENDIDIKAN TEXT," +
                "$PEKERJAAN TEXT," +
                "$SUB_PEKERJAAN_1 TEXT," +
                "$SUB_PEKERJAAN_2 TEXT," +
                "$SUB_PEKERJAAN_3 TEXT," +
                "$PENGHASILAN TEXT," +
                "$ANGGOTA TEXT," +
                "$ID_PETUGAS INTEGER)")

        val CREATE_FAMILY_TABLE = ("CREATE TABLE $TABLE_FAMILY (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NAMA TEXT," +
                "$USIA INTEGER," +
                "$PENDIDIKAN TEXT," +
                "$HK TEXT," +
                "${ID.plus("_member")} INTEGER)")

        db?.execSQL(CREATE_MEMBER_TABLE)
        db?.execSQL(CREATE_FAMILY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query_member = "DROP TABLE IF EXISTS $TABLE_MEMBER"
        val query_family = "DROP TABLE IF EXISTS $TABLE_FAMILY"

        db!!.execSQL(query_member)
        db!!.execSQL(query_family)

        onCreate(db)
    }
}