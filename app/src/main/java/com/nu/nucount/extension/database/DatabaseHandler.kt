package com.nu.nucount.extension.database

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
    protected val TABLE_DESA = "desa_"
    protected val TABLE_DUSUN = "dusun_"
    protected val TABLE_JABATAN = "jabatan_"
    protected val TABLE_KECAMATAN = "kecamatan_"
    protected val TABLE_PEKERJAAN = "pekerjaan_"
    protected val TABLE_RT = "rt_"
    protected val TABLE_RW = "rw_"
    protected val TABLE_STRUKTUR = "struktur_"
    protected val TABLE_SUBPEKERJAAN1 = "sub_pekerjaan_1"
    protected val TABLE_SUBPEKERJAAN2 = "sub_pekerjaan_2"

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
    protected val KETERANGAN = "keterangan_pendidikan"
    protected val PEKERJAAN = "pekerjaan"
    protected val SUB_PEKERJAAN_1 = "sub_pekerjaan_1"
    protected val SUB_PEKERJAAN_2 = "sub_pekerjaan_2"
    protected val SUB_PEKERJAAN_3 = "sub_pekerjaan_3"
    protected val PENGHASILAN = "penghasilan"
    protected val ANGGOTA = "anggota"
    protected val ID_PETUGAS = "id_petugas"
    protected val KATANU = "katanu"
    protected val STRUKTUR_KATANU = "struktur_katanu"
    protected val JENIS_STRUKTUR = "jenis_struktur_katanu"
    protected val PERIODE = "periode"
    protected val JABATAN = "jabatan"

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
                "$KETERANGAN TEXT," +
                "$PEKERJAAN TEXT," +
                "$SUB_PEKERJAAN_1 TEXT," +
                "$SUB_PEKERJAAN_2 TEXT," +
                "$SUB_PEKERJAAN_3 TEXT," +
                "$PENGHASILAN TEXT," +
                "$ANGGOTA TEXT," +
                "$KATANU TEXT," +
                "$STRUKTUR_KATANU TEXT," +
                "$JENIS_STRUKTUR TEXT," +
                "$JABATAN TEXT," +
                "$PERIODE TEXT," +
                "$ID_PETUGAS INTEGER)")

        val CREATE_FAMILY_TABLE = ("CREATE TABLE $TABLE_FAMILY (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NAMA TEXT," +
                "$USIA INTEGER," +
                "$PENDIDIKAN TEXT," +
                "$HK TEXT," +
                "${ID.plus("_member")} INTEGER)")

        val CREATE_DESA_TABLE = ("CREATE TABLE $TABLE_DESA (" +
                "$ID TEXT," +
                "$DESA TEXT)");

        val CREATE_DUSUN_TABLE = ("CREATE TABLE $TABLE_DUSUN (" +
                "$ID TEXT," +
                "$DUSUN TEXT)")

        val CREATE_JABATAN_TABLE = ("CREATE TABLE $TABLE_JABATAN (" +
                "$ID TEXT," +
                "$JABATAN TEXT)")

        val CREATE_KECAMATAN_TABLE = ("CREATE TABLE $TABLE_KECAMATAN (" +
                "$ID TEXT," +
                "$KECAMATAN TEXT)")

        val CREATE_PEKERJAAN_TABLE = ("CREATE TABLE $TABLE_PEKERJAAN (" +
                "$ID TEXT," +
                "$PEKERJAAN TEXT)")

        val CREATE_RT_TABLE = ("CREATE TABLE $TABLE_RT (" +
                "$ID TEXT," +
                "$RT TEXT)")

        val CREATE_RW_TABLE = ("CREATE TABLE $TABLE_RW (" +
                "$ID TEXT," +
                "$RW TEXT)")

        val CREATE_STRUKTUR_TABLE = ("CREATE TABLE $TABLE_STRUKTUR (" +
                "$ID TEXT," +
                "$JENIS_STRUKTUR TEXT)")

        val CREATE_TABLE_SUB_1_TABLE = ("CREATE TABLE $TABLE_SUBPEKERJAAN1 (" +
                "$ID TEXT," +
                "$SUB_PEKERJAAN_1 TEXT," +
                "${ID.plus("_pekerjaan")} TEXT)")

        val CREATE_TABLE_SUB_2_TABLE = ("CREATE TABLE $TABLE_SUBPEKERJAAN2 (" +
                "$ID TEXT," +
                "$SUB_PEKERJAAN_2 TEXT," +
                "${ID.plus("_sub1")} TEXT)")

        db?.execSQL(CREATE_MEMBER_TABLE)
        db?.execSQL(CREATE_FAMILY_TABLE)
        db?.execSQL(CREATE_DESA_TABLE)
        db?.execSQL(CREATE_DUSUN_TABLE)
        db?.execSQL(CREATE_JABATAN_TABLE)
        db?.execSQL(CREATE_KECAMATAN_TABLE)
        db?.execSQL(CREATE_PEKERJAAN_TABLE)
        db?.execSQL(CREATE_RT_TABLE)
        db?.execSQL(CREATE_RW_TABLE)
        db?.execSQL(CREATE_STRUKTUR_TABLE)
        db?.execSQL(CREATE_TABLE_SUB_1_TABLE)
        db?.execSQL(CREATE_TABLE_SUB_2_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query_member = "DROP TABLE IF EXISTS $TABLE_MEMBER"
        val query_family = "DROP TABLE IF EXISTS $TABLE_FAMILY"
        val query_desa = "DROP TABLE IF EXISTS $TABLE_DESA"
        val query_dusun = "DROP TABLE IF EXISTS $TABLE_DUSUN"
        val query_jabatan = "DROP TABLE IF EXISTS $TABLE_JABATAN"
        val query_kecamatan = "DROP TABLE IF EXISTS $TABLE_KECAMATAN"
        val query_pekerjaan = "DROP TABLE IF EXISTS $TABLE_PEKERJAAN"
        val query_rt = "DROP TABLE IF EXISTS $TABLE_RT"
        val query_rw = "DROP TABLE IF EXISTS $TABLE_RW"
        val query_struktur = "DROP TABLE IF EXISTS $TABLE_STRUKTUR"
        val query_sub_1 = "DROP TABLE IF EXISTS $TABLE_SUBPEKERJAAN1"
        val query_sub_2 = "DROP TABLE IF EXISTS $TABLE_SUBPEKERJAAN2"

        db!!.execSQL(query_member)
        db!!.execSQL(query_family)
        db!!.execSQL(query_desa)
        db!!.execSQL(query_dusun)
        db!!.execSQL(query_jabatan)
        db!!.execSQL(query_kecamatan)
        db!!.execSQL(query_pekerjaan)
        db!!.execSQL(query_rt)
        db!!.execSQL(query_rw)
        db!!.execSQL(query_struktur)
        db!!.execSQL(query_sub_1)
        db!!.execSQL(query_sub_2)

        onCreate(db)
    }
}