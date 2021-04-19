package com.nu.pcnucount.extension.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.nu.pcnucount.core.helper.SqlOperation
import com.nu.pcnucount.model.Jabatan
import com.nu.pcnucount.model.Kecamatan

class KecamatanOperation(context: Context): SqlOperation<Kecamatan>, DatabaseHandler(context) {

    override fun create(data: Kecamatan): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID, data.id_kecamatan)
        contentValues.put(KECAMATAN, data.nama_kecamatan)

        val success = db.insert(TABLE_KECAMATAN, null, contentValues)
        return success
    }

    override fun update(data: Kecamatan, id: Any): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Any): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_KECAMATAN,null,null)

        db.close()
        return success
    }

    override fun getAll(): List<Kecamatan> {
        val data: ArrayList<Kecamatan> = ArrayList()
        val selectQuery = "SELECT a.* " +
                "FROM $TABLE_KECAMATAN a"

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
                val kecamatan = Kecamatan(cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(KECAMATAN))
                )

                data.add(kecamatan)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }

    override fun getById(id: Any): Kecamatan {
        TODO("Not yet implemented")
    }
}