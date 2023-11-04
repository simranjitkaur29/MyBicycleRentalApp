package com.example.bicyclerentalapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper3 (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                PICK_DATE_COL + " TEXT," +
                DROP_DATE_COL + " TEXT," +
                TOTAL_PRICE_COL + " REAL," +
                BIKE_NUMBER_COL + " TEXT" + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addCancelledRide(
        pickDate: String,
        dropDate: String,
        totalPrice: Double,
        bikeNumber: String
    ) {
        val values = ContentValues()

        values.put(PICK_DATE_COL, pickDate)
        values.put(DROP_DATE_COL, dropDate)
        values.put(TOTAL_PRICE_COL, totalPrice)
        values.put(BIKE_NUMBER_COL, bikeNumber)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getCancelledRides(): Cursor? {
        val db = this.readableDatabase
        val columns = arrayOf(
            ID_COL,
            PICK_DATE_COL,
            DROP_DATE_COL,
            TOTAL_PRICE_COL,
            BIKE_NUMBER_COL
        )

        return db.query(TABLE_NAME, columns, null, null, null, null, null)
    }

    companion object {
        private const val DATABASE_NAME = "cancelled"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "cancelled_rides"
        const val ID_COL = "id"
        const val PICK_DATE_COL = "pick_date"
        const val DROP_DATE_COL = "drop_date"
        const val TOTAL_PRICE_COL = "total_price"
        const val BIKE_NUMBER_COL = "bike_number"
    }
}