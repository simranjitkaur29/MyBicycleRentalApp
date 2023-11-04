package com.example.bicyclerentalapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bicyclerentalapp.RideAdapter.RideViewHolder

class RideAdapter(private var rideList: MutableList<RideItem>, private val onRideLongClickListener: (RideItem) -> Unit):
    RecyclerView.Adapter<RideViewHolder>() {
    constructor(rideList: List<RideItem>) : this(rideList.toMutableList(), {})

    class RideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pickDateTextView: TextView = itemView.findViewById(R.id.pickDateTextView)
        val dropDateTextView: TextView = itemView.findViewById(R.id.dropDateTextView)
        val totalPriceTextView: TextView = itemView.findViewById(R.id.totalPriceTextView)
        val bikeNumberTextView: TextView = itemView.findViewById(R.id.bikeNumberTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ride, parent, false)
        return RideViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        val currentRide = rideList[position]

        holder.pickDateTextView.text = "Pick Date: ${currentRide.pickDate}"
        holder.dropDateTextView.text = "Drop Date: ${currentRide.dropDate}"
        holder.totalPriceTextView.text = "Total Price: ${currentRide.totalPrice}"
        holder.bikeNumberTextView.text = "Bike Number: ${currentRide.bikeNumber}"
        holder.itemView.setOnLongClickListener {
            onRideLongClickListener(currentRide)
            true // Consume the long click event
        }
    }
    fun removeRideByBikeNumber(bikeNumber: String) {
        val index = rideList.indexOfFirst { it.bikeNumber == bikeNumber }
        if (index != -1) {
            rideList = rideList.toMutableList().apply { removeAt(index) }
            notifyItemRemoved(index)
            notifyDataSetChanged()
        }
    }

    fun clearRides() {
        rideList.clear()
        notifyDataSetChanged()
    }
    fun addRides(newRides: List<RideItem>) {
        rideList.addAll(newRides)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return rideList.size

    }
    fun updateRides(newRides: MutableList<RideItem>) {
        rideList.clear()
        rideList.addAll(newRides)
        notifyDataSetChanged()
    }
}
