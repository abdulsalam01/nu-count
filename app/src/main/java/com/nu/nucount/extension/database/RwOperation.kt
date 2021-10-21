package com.nu.nucount.extension.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.nu.nucount.core.helper.SqlOperation
import com.nu.nucount.model.Rw

class RwOperation(context: Context) : SqlOperation<Rw>, DatabaseHandler(context) {

    override fun create(data: Rw): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID, data.id_rw)
        contentValues.put(RW, data.nama_rw)

        val success = db.insert(TABLE_RW, null, contentValues)
        return success
    }

    override fun update(data: Rw, id: Any): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Any): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_RW,null,null)

        db.close()
        return success
    }

    override fun getAll(): List<Rw> {
        val data: ArrayList<Rw> = ArrayList()
        val selectQuery = "SELECT a.* " +
                "FROM $TABLE_RW a"

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
                val rw = Rw(cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(RW))
                )

                data.add(rw)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }

    override fun getById(id: Any): Rw {
        TODO("Not yet implemented")
    }
}