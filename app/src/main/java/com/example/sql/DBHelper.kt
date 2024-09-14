package com.example.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){
    companion object{
        private const val DATABASE_NAME = "PERSON_DATABASE"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "person_table"
        const val KEY_ID = "id"
        const val KEY_NAME = "name"
        const val KEY_SURNAME = "surname"
        const val KEY_PHONE = "phone"
        const val KEY_POST = "post"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME " +
                "($KEY_ID INTEGER PRIMARY KEY, $KEY_NAME TEXT, " +
                "$KEY_SURNAME TEXT, $KEY_PHONE TEXT, $KEY_POST TEXT)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun addPerson(name: String, surname: String, phone: String, post: String){
        val values = ContentValues()
        values.put(KEY_NAME, name)
        values.put(KEY_SURNAME, surname)
        values.put(KEY_PHONE, phone)
        values.put(KEY_POST, post)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getInfo(): Cursor?{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun removeAll(){
        val db = writableDatabase
    db.delete(TABLE_NAME, null, null)
    }
}