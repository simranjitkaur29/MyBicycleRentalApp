package com.example.bicyclerentalapp



// PreviousFragment.kt
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bicyclerentalapp.DBHelper2
import com.example.bicyclerentalapp.RideAdapter
import com.example.bicyclerentalapp.RideItem

class PreviousFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var rideAdapter: RideAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_previous, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.previousRcycle) // Make sure to add this RecyclerView in your fragment_previous.xml layout
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        rideAdapter = RideAdapter(emptyList()) // Initialize with an empty list

        recyclerView.adapter = rideAdapter

        // Load and display previous rides
        loadAndDisplayPreviousRides()
    }

    private fun loadAndDisplayPreviousRides() {
        Log.d("DBHelper", "loadAndDisplayPreviousRides called")
        val dbHelper = DBHelper2(requireContext(), null)
        val previousRides = dbHelper.getRides()
        rideAdapter.clearRides()
        rideAdapter.updateRides(mapCursorToRideItemList(previousRides) as MutableList<RideItem>)
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
}