package com.nu.pcnucount.extension.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.nu.pcnucount.core.helper.SqlOperation
import com.nu.pcnucount.model.Desa
import com.nu.pcnucount.model.Family

class DesaOperation(context: Context) : SqlOperation<Desa>, DatabaseHandler(context) {

    override fun create(data: Desa): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID, data.id_desa)
        contentValues.put(DESA, data.nama_desa)

        val success = db.insert(TABLE_DESA, null, contentValues)
        return success
    }

    override fun update(data: Desa, id: Any): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Any): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_DESA,null,null)

        db.close()
        return success
    }

    override fun getAll(): List<Desa> {
        val data: ArrayList<Desa> = ArrayList()
        val selectQuery = "SELECT a.* " +
                "FROM $TABLE_DESA a"

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
                val desa = Desa(cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(DESA))
                )

                data.add(desa)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }

    override fun getById(id: Any): Desa {
        TODO("Not yet implemented")
    }

}