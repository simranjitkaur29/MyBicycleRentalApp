package com.example.bicyclerentalapp
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper2(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                PICK_DATE_COL + " TEXT," +
                DROP_DATE_COL + " TEXT," +
                TOTAL_PRICE_COL + " REAL," +
                BIKE_NUMBER_COL + " TEXT," +
                STATUS_COL + " TEXT" + ")")
        db.execSQL(query)
    }


    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addRide(pickDate: String, dropDate: String, totalPrice: Double, bikeNumber: String) {
        val values = ContentValues()
        values.put(PICK_DATE_COL, pickDate)
        values.put(DROP_DATE_COL, dropDate)
        values.put(TOTAL_PRICE_COL, totalPrice)
        values.put(BIKE_NUMBER_COL, bikeNumber)

        if (!isRideAlreadyExists(pickDate, dropDate, totalPrice, bikeNumber)) {
            val db = this.writableDatabase
            db.insert(TABLE_NAME, null, values)
            db.close()
        } else {
            // Handle case where the ride already exists
            Log.d("DBHelper", "Ride already exists: $pickDate, $dropDate, $totalPrice, $bikeNumber")
        }
    }

    private fun isRideAlreadyExists(pickDate: String, dropDate: String, totalPrice: Double, bikeNumber: String): Boolean {
        val db = this.readableDatabase
        val selection = "$PICK_DATE_COL=? AND $DROP_DATE_COL=? AND $TOTAL_PRICE_COL=? AND $BIKE_NUMBER_COL=?"
        val selectionArgs = arrayOf(pickDate, dropDate, totalPrice.toString(), bikeNumber)
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)
        val rideExists = cursor.count > 0
        cursor.close()
        return rideExists
    }



    fun getRides(): Cursor? {
        val db = this.readableDatabase
        val columns = arrayOf(
            ID_COL,
            PICK_DATE_COL,
            DROP_DATE_COL,
            TOTAL_PRICE_COL,
            BIKE_NUMBER_COL
        )
        val orderBy = "$ID_COL DESC" // Order by ID in descending order

        return db.query(TABLE_NAME, columns, null, null, null, null, orderBy)
    }

    fun getRide() :  Cursor?{
        val db = this.readableDatabase
         return db.rawQuery("SELECT * FROM "+ TABLE_NAME, null)
    }
    fun viewEmployee():List<RideItem>{
        val empList:ArrayList<RideItem> = ArrayList<RideItem>()
        val selectQuery = "select  * from $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: String
        var userName: String
        var userEmail: Double
        var userSalary :  String
        if (cursor.moveToFirst()) {
            do {
                 userId = cursor.getString(cursor.getColumnIndex(DBHelper2.PICK_DATE_COL))
                 userName = cursor.getString(cursor.getColumnIndex(DBHelper2.DROP_DATE_COL))
                 userEmail = cursor.getDouble(cursor.getColumnIndex(DBHelper2.TOTAL_PRICE_COL))
                 userSalary= cursor.getString(cursor.getColumnIndex(DBHelper2.BIKE_NUMBER_COL))

                val emp= RideItem(pickDate = userId, dropDate = userName, totalPrice = userEmail, bikeNumber = userSalary )
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

    fun deleteRide(bikeNumber: String): Int {
        val db = this.writableDatabase
        val whereClause = "$BIKE_NUMBER_COL = ?"
        val whereArgs = arrayOf(bikeNumber)
        val deleted = db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
        return deleted
    }
    fun updateRidesInAdapter(adapter: RideAdapter, canceledBikeNumber: String? = null) {
        Log.d("DBHelper", "updateRidesInAdapter called with canceledBikeNumber: $canceledBikeNumber")
        val rides = getRides()
        rides?.use {
            val updatedList = mutableListOf<RideItem>()
            while (it.moveToNext()) {
                val pickDate = it.getString(it.getColumnIndexOrThrow(PICK_DATE_COL))
                val dropDate = it.getString(it.getColumnIndexOrThrow(DROP_DATE_COL))
                val totalPrice = it.getDouble(it.getColumnIndexOrThrow(TOTAL_PRICE_COL))
                val bikeNumber = it.getString(it.getColumnIndexOrThrow(BIKE_NUMBER_COL))

                if (canceledBikeNumber == null ||bikeNumber != canceledBikeNumber) {
                    val ride = RideItem(pickDate, dropDate, totalPrice, bikeNumber)
                    updatedList.add(ride)
                }
                else
                {
                    Log.d("DBHelper", "Skipped canceled ride with bike number: $canceledBikeNumber")
                }
            }
            adapter.updateRides(updatedList)
        }
    }

    companion object {
        private const val DATABASE_NAME = "history"
        private const val DATABASE_VERSION = 2
        const val TABLE_NAME = "my_table1"
        const val ID_COL = "id"
        const val PICK_DATE_COL = "pick_date"
        const val DROP_DATE_COL = "drop_date"
        const val TOTAL_PRICE_COL = "total_price"
        const val BIKE_NUMBER_COL = "bike_number"
        const val STATUS_COL = "status"
    }
}
