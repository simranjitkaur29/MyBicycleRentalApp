package com.example.bicyclerentalapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "SignLog.db", null, 2) {
    companion object {
        const val TABLE_NAME = "users"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS $TABLE_NAME " +
                "($COLUMN_USERNAME TEXT PRIMARY KEY, $COLUMN_PASSWORD TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USERNAME, username)
        contentValues.put(COLUMN_PASSWORD, password)
        val result = db.insert(TABLE_NAME, null, contentValues) != -1L
        db.close()
        return result
    }

    fun checkUsername(username: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ?", arrayOf(username))
        val result = cursor.count > 0
        cursor.close()
        db.close()
        return result
    }

    fun checkUsernamePassword(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(username, password)
        )
        val result = cursor.count > 0
        cursor.close()
        db.close()
        return result
    }

    fun isValidCredentials(username: String, password: String): Boolean {
        try {
            val db = this.readableDatabase
            val cursor = db.rawQuery(
                "SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?",
                arrayOf(username, password)
            )

            if (cursor != null) {
                Log.d("DatabaseHelper", "Query successful")
                Log.d("DatabaseHelper", "Rows in cursor: ${cursor.count}")

                val result = cursor.count > 0
                Log.d("DatabaseHelper", "Credentials valid: $result")

                cursor.close()
                db.close()
                return result
            } else {
                Log.e("DatabaseHelper", "Cursor is null")
            }

        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error checking credentials: ${e.message}")
        }

        return false
    }

    // Assuming this function is in your DatabaseHelper class

    fun updatePasswordAsync(username: String, newPassword: String, callback: (Boolean) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val db = writableDatabase
                val contentValues = ContentValues()
                contentValues.put(COLUMN_PASSWORD, newPassword)

                val rowsUpdated = db.update(TABLE_NAME, contentValues, "$COLUMN_USERNAME=?", arrayOf(username))

                db.close()

                callback(rowsUpdated > 0)
            } catch (e: Exception) {
                Log.e("DatabaseHelper", "Error updating password: ${e.message}")
                callback(false)
            }
        }
    }
}