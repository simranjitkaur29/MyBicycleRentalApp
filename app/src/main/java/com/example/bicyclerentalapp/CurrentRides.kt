package com.example.bicyclerentalapp


import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bicyclerentalapp.DBHelper2
import com.example.bicyclerentalapp.DBHelper3
import com.example.bicyclerentalapp.RideAdapter
import com.example.bicyclerentalapp.RideItem

class CurrentRides : AppCompatActivity() {

    private lateinit var currentRideRecycler: RecyclerView
    private lateinit var rideAdapter: RideAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_rides)

        currentRideRecycler = findViewById(R.id.current_ride_recycler)
        rideAdapter = RideAdapter(mutableListOf()) { rideItem -> onRideLongPressed(rideItem) }
        currentRideRecycler.layoutManager = LinearLayoutManager(this)
        currentRideRecycler.adapter = rideAdapter

        // Load and display current rides
        loadAndDisplayCurrentRides()
    }

    private fun loadAndDisplayCurrentRides() {
        val dbHelper = DBHelper2(this, null)
        val currentRides = dbHelper.getRides()
        rideAdapter.clearRides()
        rideAdapter.updateRides(mapCursorToRideItemList(currentRides) as MutableList<RideItem>)
    }

    private fun mapCursorToRideItemList(cursor: Cursor?): List<RideItem> {
        val rideList = mutableListOf<RideItem>()

        cursor?.use {
            while (it.moveToNext()) {
                val pickDate = it.getString(it.getColumnIndex(DBHelper2.PICK_DATE_COL))
                val dropDate = it.getString(it.getColumnIndex(DBHelper2.DROP_DATE_COL))
                val totalPrice = it.getDouble(it.getColumnIndex(DBHelper2.TOTAL_PRICE_COL))
                val bikeNumber = it.getString(it.getColumnIndex(DBHelper2.BIKE_NUMBER_COL))

                val rideItem = RideItem(pickDate, dropDate, totalPrice, bikeNumber)
                rideList.add(rideItem)
            }
        }

        return rideList
    }

    private fun onRideLongPressed(rideItem: RideItem) {
        showDeleteConfirmationDialog(rideItem)
    }

    private fun showDeleteConfirmationDialog(rideItem: RideItem) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Delete Ride")
        alertDialogBuilder.setMessage("Do you want to delete this ride?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            moveRideToCancelled(rideItem)
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun moveRideToCancelled(rideItem: RideItem) {
        // Add the canceled ride to the canceled rides database
        val db = DBHelper3(this, null)
        db.addCancelledRide(rideItem.pickDate, rideItem.dropDate, rideItem.totalPrice, rideItem.bikeNumber)

        // Remove the canceled ride from the current rides database
        val dbHelper = DBHelper2(this, null)
        val deleted = dbHelper.deleteRide(rideItem.bikeNumber)
        rideAdapter.removeRideByBikeNumber(rideItem.bikeNumber)
        Log.d("RideDeletion", "Deleted ride with bike number ${rideItem.bikeNumber}: $deleted")
    }
}

