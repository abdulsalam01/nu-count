package com.nu.nucount.extension.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.nu.nucount.core.helper.SqlOperation
import com.nu.nucount.model.Pekerjaan

class PekerjaanOperation(context: Context) : SqlOperation<Pekerjaan>, DatabaseHandler(context) {

    override fun create(data: Pekerjaan): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID, data.id_pekerjaan)
        contentValues.put(PEKERJAAN, data.nama_pekerjaan)

        val success = db.insert(TABLE_PEKERJAAN, null, contentValues)
        return success
    }

    override fun update(data: Pekerjaan, id: Any): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Any): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_PEKERJAAN,null,null)

        db.close()
        return success
    }

    override fun getAll(): List<Pekerjaan> {
        val data: ArrayList<Pekerjaan> = ArrayList()
        val selectQuery = "SELECT a.* " +
                "FROM $TABLE_PEKERJAAN a"

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
                val pekerjaan = Pekerjaan(cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(PEKERJAAN))
                )

                data.add(pekerjaan)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }

    override fun getById(id: Any): Pekerjaan {
        TODO("Not yet implemented")
    }
}