package com.example.bicyclerentalapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class BookRideAdapter(var context : Context, var list : ArrayList<StoreRides>) : Adapter<BookRideAdapter.vie>() {
    inner class vie(v :  View) : ViewHolder(v){

         var bikenum :  TextView = v.findViewById(R.id.bikenum)
         var stop :  TextView = v.findViewById(R.id.location)
         var price :  TextView = v.findViewById(R.id.money)
         var status :  TextView = v.findViewById(R.id.available)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vie {
        val view =  LayoutInflater.from(context).inflate(R.layout.ride_list , parent , false)
        return vie(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: vie, position: Int) {
        var item = list[position]
        holder.bikenum.text=item.bike
        holder.stop.text=item.stop
        holder.price.text=item.price
        holder.status.text=item.status
       holder.itemView.setOnClickListener{
           var i  = Intent(context , CycleDetails::class.java)
           i.putExtra("name" , item.bike.toString())
           i.putExtra("price",item.price.toString())
           i.putExtra("status",item.status.toString())
           i.putExtra("stop",item.stop.toString())
           context.startActivity(i)
       }
    }
}