package com.nu.nucount.extension.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.nu.nucount.core.helper.SqlOperation
import com.nu.nucount.model.Rt

class RtOperation(context: Context) : SqlOperation<Rt>, DatabaseHandler(context) {

    override fun create(data: Rt): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID, data.id_rt)
        contentValues.put(RT, data.nama_rt)

        val success = db.insert(TABLE_RT, null, contentValues)
        return success
    }

    override fun update(data: Rt, id: Any): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Any): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_RT,null,null)

        db.close()
        return success
    }

    override fun getAll(): List<Rt> {
        val data: ArrayList<Rt> = ArrayList()
        val selectQuery = "SELECT a.* " +
                "FROM $TABLE_RT a"

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
                val rt = Rt(cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(RT))
                )

                data.add(rt)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }

    override fun getById(id: Any): Rt {
        TODO("Not yet implemented")
    }
}