package com.nu.nucount.extension.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.nu.nucount.core.helper.SqlOperation
import com.nu.nucount.model.Jabatan

class JabatanOperation(context: Context): SqlOperation<Jabatan>, DatabaseHandler(context) {

    override fun create(data: Jabatan): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID, data.id_jabatan)
        contentValues.put(JABATAN, data.nama_jabatan)

        val success = db.insert(TABLE_JABATAN, null, contentValues)
        return success
    }

    override fun update(data: Jabatan, id: Any): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Any): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_JABATAN,null,null)

        db.close()
        return success
    }

    override fun getAll(): List<Jabatan> {
        val data: ArrayList<Jabatan> = ArrayList()
        val selectQuery = "SELECT a.* " +
                "FROM $TABLE_JABATAN a"

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
                val jabatan = Jabatan(cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(JABATAN))
                )

                data.add(jabatan)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }

    override fun getById(id: Any): Jabatan {
        TODO("Not yet implemented")
    }

}