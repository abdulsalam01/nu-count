package com.nu.nucount.extension.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.nu.nucount.core.helper.SqlOperation
import com.nu.nucount.model.Struktur

class StrukturOperation(context: Context) : SqlOperation<Struktur>, DatabaseHandler(context) {

    override fun create(data: Struktur): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID, data.id_struktur)
        contentValues.put(JENIS_STRUKTUR, data.nama_struktur)

        val success = db.insert(TABLE_STRUKTUR, null, contentValues)
        return success
    }

    override fun update(data: Struktur, id: Any): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Any): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_STRUKTUR,null,null)

        db.close()
        return success
    }

    override fun getAll(): List<Struktur> {
        val data: ArrayList<Struktur> = ArrayList()
        val selectQuery = "SELECT a.* " +
                "FROM $TABLE_STRUKTUR a"

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
                val struktur = Struktur(cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(JENIS_STRUKTUR))
                )

                data.add(struktur)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }

    override fun getById(id: Any): Struktur {
        TODO("Not yet implemented")
    }
}