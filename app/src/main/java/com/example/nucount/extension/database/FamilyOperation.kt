package com.example.nucount.extension.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.example.nucount.core.helper.SqlOperation
import com.example.nucount.model.Family
import kotlin.collections.ArrayList

class FamilyOperation(context: Context) : SqlOperation<Family>, DatabaseHandler(context) {

    override fun create(data: Family): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(NAMA, data.nama)
        contentValues.put(HK, data.hk)
        contentValues.put(USIA, data.usia)
        contentValues.put(PENDIDIKAN, data.pendidikan)
        contentValues.put(ID.plus("_member"), data.idMember)

        val success = db.insert(TABLE_FAMILY, null, contentValues)

        db.close()
        return success
    }

    override fun update(data: Family, id: Any): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Any): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_FAMILY,"${ID.plus("_member")} = $id",null)

        db.close()
        return success
    }

    override fun deleteAll(): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_FAMILY,null,null)

        db.close()
        return success
    }

    override fun getAll(): List<Family> {
        val data: ArrayList<Family> = ArrayList()
        val selectQuery = "SELECT a.* " +
                "FROM $TABLE_FAMILY a"

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
                val family = Family(cursor.getString(cursor.getColumnIndex(NAMA)),
                    cursor.getString(cursor.getColumnIndex(USIA)),
                    cursor.getString(cursor.getColumnIndex(HK)),
                    cursor.getString(cursor.getColumnIndex(PENDIDIKAN)),
                    cursor.getInt(cursor.getColumnIndex(ID.plus("_member")))
                )

                data.add(family)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }

    fun getAllByIdMember(idMember: Int): List<Family> {
        val data: ArrayList<Family> = ArrayList()
        val selectQuery = "SELECT * " +
                "FROM $TABLE_FAMILY a " +
                "WHERE ${ID.plus("_member")} = $idMember"

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
                val family = Family(cursor.getString(cursor.getColumnIndex(NAMA)),
                    cursor.getString(cursor.getColumnIndex(USIA)),
                    cursor.getString(cursor.getColumnIndex(HK)),
                    cursor.getString(cursor.getColumnIndex(PENDIDIKAN)),
                    cursor.getInt(cursor.getColumnIndex(ID.plus("_member")))
                )

                data.add(family)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return data
    }

    override fun getById(id: Any): Family {
        TODO()
    }
}