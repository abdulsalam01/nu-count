package com.nu.nucount.extension.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.nu.nucount.core.helper.SqlOperation
import com.nu.nucount.model.Dusun

class DusunOperation(context: Context) : SqlOperation<Dusun>, DatabaseHandler(context) {

    override fun create(data: Dusun): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID, data.id_dusun)
        contentValues.put(DUSUN, data.nama_dusun)

        val success = db.insert(TABLE_DUSUN, null, contentValues)
        return success
    }

    override fun update(data: Dusun, id: Any): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Any): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_DUSUN,null,null)

        db.close()
        return success
    }

    override fun getAll(): List<Dusun> {
        val data: ArrayList<Dusun> = ArrayList()
        val selectQuery = "SELECT a.* " +
                "FROM $TABLE_DUSUN a"

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
                val dusun = Dusun(cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(DUSUN))
                )

                data.add(dusun)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }

    override fun getById(id: Any): Dusun {
        TODO("Not yet implemented")
    }

}