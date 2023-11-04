package com.example.bicyclerentalapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class currentride2(var c : Context,var list: List<RideItem> ) : Adapter<currentride2.viewhol>() {
    inner class  viewhol(v : View) : ViewHolder(v){
        var pick  = v.findViewById<TextView>(R.id.pickDateTextView)
        var drop  = v.findViewById<TextView>(R.id.dropDateTextView)
        var tprice  = v.findViewById<TextView>(R.id.totalPriceTextView)
        var bn  = v.findViewById<TextView>(R.id.bikeNumberTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewhol {
        return  viewhol(LayoutInflater.from(c).inflate(R.layout.item_ride , parent , false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewhol, position: Int) {
       val i =  list[position]
        holder.pick.text = i.pickDate
        holder.drop.text = i.dropDate
        holder.tprice.text = i.pickDate
        holder.bn.text = i.bikeNumber
    }
}