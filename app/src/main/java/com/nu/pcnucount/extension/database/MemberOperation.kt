package com.nu.pcnucount.extension.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.nu.pcnucount.core.helper.SqlOperation
import com.nu.pcnucount.model.Family
import com.nu.pcnucount.model.Member
import kotlin.collections.ArrayList

class MemberOperation(val context: Context) : SqlOperation<Member>, DatabaseHandler(context) {

    override fun create(data: Member): Long {
        val db = this.writableDatabase
        var success = 0L

        db.beginTransaction()
        try {
            val contentValues = ContentValues()

            contentValues.put(NAMA_LENGKAP, data.namaLengkap)
            contentValues.put(NIK, data.nik)
            contentValues.put(STATUS_NIKAH, data.statusNikah)
            contentValues.put(TANGGAL_LAHIR, data.tanggalLahir.toString())
            contentValues.put(TEMPAT_LAHIR, data.tempatLahir)
            contentValues.put(JENIS_KELAMIN, data.jenisKelamin)
            contentValues.put(NOMOR, data.nomor)
            contentValues.put(KABUPATEN, data.kabupaten)
            contentValues.put(KECAMATAN, data.kecamatan)
            contentValues.put(DESA, data.desa)
            contentValues.put(DUSUN, data.dusun)
            contentValues.put(RT, data.rt)
            contentValues.put(RW, data.rw)
            contentValues.put(PENDIDIKAN, data.pendidikan)
            contentValues.put(KETERANGAN, data.keterangan)
            contentValues.put(PEKERJAAN, data.pekerjaan)
            contentValues.put(SUB_PEKERJAAN_1, data.subPekerjaan1)
            contentValues.put(SUB_PEKERJAAN_2, data.subPekerjaan2)
            contentValues.put(SUB_PEKERJAAN_3, data.subPekerjaan3)
            contentValues.put(ANGGOTA, data.anggota)
            contentValues.put(PENGHASILAN, data.penghasilan)
            contentValues.put(KATANU, data.katanu)
            contentValues.put(STRUKTUR_KATANU, data.struktur)
            contentValues.put(JENIS_STRUKTUR, data.jenisStruktur)
            contentValues.put(JABATAN, data.jabatan)
            contentValues.put(PERIODE, data.periode)
            contentValues.put(ID_PETUGAS, data.idPetugas)

            success = db.insert(TABLE_MEMBER, null, contentValues)

            if (success > 0) {

                if (data.family!!.size > 0) {
                    val familyOperation = FamilyOperation(context)

                    for (values in data.family) {
                        val family = Family(values.nama, values.usia,
                            values.hk, values.pendidikan, success.toInt())

                        familyOperation.create(family, db)
                    }
                }

                db.setTransactionSuccessful()
            }
        } catch (e: Exception) {
            e.message
        } finally {
            db.endTransaction()
            db.close()
        }

        return success
    }

    override fun update(data: Member, id: Any): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Any): Int {
        val familyOperation = FamilyOperation(context)
        val db = this.writableDatabase
        var success = 0

        db.beginTransaction()
        try {
            success = db.delete(TABLE_MEMBER, "$ID = $id", null)
            familyOperation.delete(success, db)
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            e.message
        } finally {
            db.endTransaction()
            db.close()
        }

        return success
    }

    override fun getAll(): List<Member> {
        val data: ArrayList<Member> = ArrayList()
        val selectQuery = "SELECT a.* " +
                "FROM $TABLE_MEMBER a"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {
                val family = FamilyOperation(context).
                    getAllByIdMember(cursor.getInt(cursor.getColumnIndex(ID)))

                val member = Member(cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(NAMA_LENGKAP)),
                    cursor.getString(cursor.getColumnIndex(NIK)),
                    cursor.getString(cursor.getColumnIndex(STATUS_NIKAH)),
                    cursor.getString(cursor.getColumnIndex(TANGGAL_LAHIR)),
                    cursor.getString(cursor.getColumnIndex(TEMPAT_LAHIR)),
                    cursor.getString(cursor.getColumnIndex(JENIS_KELAMIN)),
                    cursor.getString(cursor.getColumnIndex(NOMOR)),
                    cursor.getString(cursor.getColumnIndex(KABUPATEN)),
                    cursor.getString(cursor.getColumnIndex(KECAMATAN)),
                    cursor.getString(cursor.getColumnIndex(DESA)),
                    cursor.getString(cursor.getColumnIndex(DUSUN)),
                    cursor.getString(cursor.getColumnIndex(RT)),
                    cursor.getString(cursor.getColumnIndex(RW)),
                    cursor.getString(cursor.getColumnIndex(PENDIDIKAN)),
                    cursor.getString(cursor.getColumnIndex(KETERANGAN)),
                    cursor.getString(cursor.getColumnIndex(PEKERJAAN)),
                    cursor.getString(cursor.getColumnIndex(SUB_PEKERJAAN_1)),
                    cursor.getString(cursor.getColumnIndex(SUB_PEKERJAAN_2)),
                    cursor.getString(cursor.getColumnIndex(SUB_PEKERJAAN_3)),
                    cursor.getString(cursor.getColumnIndex(PENGHASILAN)),
                    cursor.getString(cursor.getColumnIndex(ANGGOTA)),
                    cursor.getString(cursor.getColumnIndex(KATANU)),
                    cursor.getString(cursor.getColumnIndex(STRUKTUR_KATANU)),
                    cursor.getString(cursor.getColumnIndex(JENIS_STRUKTUR)),
                    cursor.getString(cursor.getColumnIndex(JABATAN)),
                    cursor.getString(cursor.getColumnIndex(PERIODE)),
                    cursor.getInt(cursor.getColumnIndex(ID_PETUGAS)),
                    family
                )

                data.add(member)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }

    override fun getById(id: Any): Member {
        TODO()
    }

    fun getByIdPetugas(id: Any): List<Member> {
        val data: ArrayList<Member> = ArrayList()
        val selectQuery = "SELECT a.* " +
                "FROM $TABLE_MEMBER a " +
                "WHERE $ID_PETUGAS = $id"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {
                val family = FamilyOperation(context).
                getAllByIdMember(cursor.getInt(cursor.getColumnIndex(ID)))

                val member = Member(
                    cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(NAMA_LENGKAP)),
                    cursor.getString(cursor.getColumnIndex(NIK)),
                    cursor.getString(cursor.getColumnIndex(STATUS_NIKAH)),
                    cursor.getString(cursor.getColumnIndex(TANGGAL_LAHIR)),
                    cursor.getString(cursor.getColumnIndex(TEMPAT_LAHIR)),
                    cursor.getString(cursor.getColumnIndex(JENIS_KELAMIN)),
                    cursor.getString(cursor.getColumnIndex(NOMOR)),
                    cursor.getString(cursor.getColumnIndex(KABUPATEN)),
                    cursor.getString(cursor.getColumnIndex(KECAMATAN)),
                    cursor.getString(cursor.getColumnIndex(DESA)),
                    cursor.getString(cursor.getColumnIndex(DUSUN)),
                    cursor.getString(cursor.getColumnIndex(RT)),
                    cursor.getString(cursor.getColumnIndex(RW)),
                    cursor.getString(cursor.getColumnIndex(PENDIDIKAN)),
                    cursor.getString(cursor.getColumnIndex(KETERANGAN)),
                    cursor.getString(cursor.getColumnIndex(PEKERJAAN)),
                    cursor.getString(cursor.getColumnIndex(SUB_PEKERJAAN_1)),
                    cursor.getString(cursor.getColumnIndex(SUB_PEKERJAAN_2)),
                    cursor.getString(cursor.getColumnIndex(SUB_PEKERJAAN_3)),
                    cursor.getString(cursor.getColumnIndex(PENGHASILAN)),
                    cursor.getString(cursor.getColumnIndex(ANGGOTA)),
                    cursor.getString(cursor.getColumnIndex(KATANU)),
                    cursor.getString(cursor.getColumnIndex(STRUKTUR_KATANU)),
                    cursor.getString(cursor.getColumnIndex(JENIS_STRUKTUR)),
                    cursor.getString(cursor.getColumnIndex(JABATAN)),
                    cursor.getString(cursor.getColumnIndex(PERIODE)),
                    cursor.getInt(cursor.getColumnIndex(ID_PETUGAS)),
                    family
                )

                data.add(member)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }

    override fun deleteAll(): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_MEMBER,null,null)

        db.close()
        return success
    }

    fun count(): Int {
        val db = this.readableDatabase
        val query = "SELECT $ID FROM $TABLE_MEMBER"
        val cursor: Cursor? = db.rawQuery(query, null)
        val count = cursor!!.count

        db.close()
        cursor.close()

        return count
    }

    fun countByIdSession(id: Any): Int {
        val db = this.readableDatabase
        val query = "SELECT $ID FROM $TABLE_MEMBER WHERE $ID_PETUGAS = $id"
        val cursor: Cursor? = db.rawQuery(query, null)
        val count = cursor!!.count

        db.close()
        cursor.close()

        return count
    }
}