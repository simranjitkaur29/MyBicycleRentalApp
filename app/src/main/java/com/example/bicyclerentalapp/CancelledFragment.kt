package com.example.bicyclerentalapp
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bicyclerentalapp.DBHelper3
import com.example.bicyclerentalapp.RideAdapter
import com.example.bicyclerentalapp.RideItem

class CancelledFragment : Fragment(R.layout.fragment_cancelled) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var rideAdapter: RideAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.cancelRide)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Fetch all canceled rides from the database
        val cancelledRides: List<RideItem> = getCancelledRidesFromDatabase()

        // Initialize the adapter with the canceled rides
        rideAdapter = RideAdapter(cancelledRides)
        recyclerView.adapter = rideAdapter
    }

    private fun getCancelledRidesFromDatabase(): List<RideItem> {
        val dbHelperCancelled = DBHelper3(requireContext(), null)
        val cursor = dbHelperCancelled.getCancelledRides()
        val rides = mutableListOf<RideItem>()

        cursor?.use {
            while (it.moveToNext()) {
                val pickDate = it.getString(it.getColumnIndexOrThrow(DBHelper3.PICK_DATE_COL))
                val dropDate = it.getString(it.getColumnIndexOrThrow(DBHelper3.DROP_DATE_COL))
                val totalPrice = it.getDouble(it.getColumnIndexOrThrow(DBHelper3.TOTAL_PRICE_COL))
                val bikeNumber = it.getString(it.getColumnIndexOrThrow(DBHelper3.BIKE_NUMBER_COL))

                val ride = RideItem(pickDate, dropDate, totalPrice, bikeNumber)
                rides.add(ride)
            }
        }

        return rides
    }
}
