package com.nu.nucount.extension.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.nu.nucount.core.helper.SqlOperation
import com.nu.nucount.model.SubPekerjaan2

class SubPekerjaan2Operation(context: Context): SqlOperation<SubPekerjaan2>, DatabaseHandler(context) {

    override fun create(data: SubPekerjaan2): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID, data.id_sub2)
        contentValues.put(SUB_PEKERJAAN_2, data.nama_sub2)
        contentValues.put(ID.plus("_sub1"), data.id_tb_sub1)

        val success = db.insert(TABLE_SUBPEKERJAAN2, null, contentValues)
        return success
    }

    override fun update(data: SubPekerjaan2, id: Any): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Any): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_SUBPEKERJAAN2,null,null)

        db.close()
        return success
    }

    override fun getAll(): List<SubPekerjaan2> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Any): SubPekerjaan2 {
        TODO("Not yet implemented")
    }

    fun getByIds(id: Any): List<SubPekerjaan2> {
        val data: ArrayList<SubPekerjaan2> = ArrayList()
        val selectQuery = "SELECT a.* " +
                "FROM $TABLE_SUBPEKERJAAN2 a " +
                "WHERE ${ID.plus("_sub1")} = '$id'"

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
                val subPekerjaan2 = SubPekerjaan2(cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(SUB_PEKERJAAN_2)),
                    cursor.getString(cursor.getColumnIndex(ID.plus("_sub1")))
                )

                data.add(subPekerjaan2)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }
}