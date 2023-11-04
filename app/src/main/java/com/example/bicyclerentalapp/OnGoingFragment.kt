package com.example.bicyclerentalapp
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton

class OnGoingFragment : Fragment() {
    private lateinit var pickDateTextView: TextView
    private lateinit var dropDateTextView: TextView
    private lateinit var totalPriceTextView: TextView
    private lateinit var bikeNumberTextView: TextView
    private lateinit var refreshButton: FloatingActionButton
   // private val ongoingViewModel: OnGoingViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_going, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pickDateTextView = view.findViewById(R.id.pickDateTextView)
        dropDateTextView = view.findViewById(R.id.dropDateTextView)
        totalPriceTextView = view.findViewById(R.id.totalPriceTextView)
        bikeNumberTextView = view.findViewById(R.id.bikeNumberTextView)
        refreshButton = view.findViewById(R.id.refreshButton)

        refreshButton.setOnClickListener {
            activity?.recreate()
        }
        val db = DBHelper2(requireContext(), null)
        val cursor = db.getRides()

        while (cursor != null && cursor.moveToNext()) {
            val pickDate = cursor.getString(cursor.getColumnIndex(DBHelper2.PICK_DATE_COL))
            val dropDate = cursor.getString(cursor.getColumnIndex(DBHelper2.DROP_DATE_COL))
            val totalPrice = cursor.getDouble(cursor.getColumnIndex(DBHelper2.TOTAL_PRICE_COL))
            val bikeNumber = cursor.getString(cursor.getColumnIndex(DBHelper2.BIKE_NUMBER_COL))
            Log.d("OnGoingFragment", "Retrieved data: $pickDate, $dropDate, $totalPrice, $bikeNumber")

            db.addRide(pickDate, dropDate, totalPrice, bikeNumber)

            // Set the values for the last item in the cursor
            pickDateTextView.text = "Pick Date: $pickDate"
            dropDateTextView.text = "Drop Date: $dropDate"
            totalPriceTextView.text = "Total Price: $totalPrice"
            bikeNumberTextView.text = "Bike Number: $bikeNumber"
        }

        cursor?.close()
    }
    }
