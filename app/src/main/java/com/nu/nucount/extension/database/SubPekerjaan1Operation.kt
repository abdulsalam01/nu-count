package com.nu.nucount.extension.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.nu.nucount.core.helper.SqlOperation
import com.nu.nucount.model.SubPekerjaan1

class SubPekerjaan1Operation(context: Context): SqlOperation<SubPekerjaan1>, DatabaseHandler(context) {

    override fun create(data: SubPekerjaan1): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID, data.id_sub)
        contentValues.put(SUB_PEKERJAAN_1, data.nama_sub)
        contentValues.put(ID.plus("_pekerjaan"), data.id_tb_pekerjaan)

        val success = db.insert(TABLE_SUBPEKERJAAN1, null, contentValues)
        return success
    }

    override fun update(data: SubPekerjaan1, id: Any): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Any): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_SUBPEKERJAAN1,null,null)

        db.close()
        return success
    }

    override fun getAll(): List<SubPekerjaan1> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Any): SubPekerjaan1 {
        TODO("Not yet implemented")
    }

    fun getByIds(id: Any): List<SubPekerjaan1> {
        val data: ArrayList<SubPekerjaan1> = ArrayList()
        val selectQuery = "SELECT a.* " +
                "FROM $TABLE_SUBPEKERJAAN1 a " +
                "WHERE ${ID.plus("_pekerjaan")} = '$id'"

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
                val subPekerjaan1 = SubPekerjaan1(cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(SUB_PEKERJAAN_1)),
                    cursor.getString(cursor.getColumnIndex(ID.plus("_pekerjaan")))
                )

                data.add(subPekerjaan1)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }
}