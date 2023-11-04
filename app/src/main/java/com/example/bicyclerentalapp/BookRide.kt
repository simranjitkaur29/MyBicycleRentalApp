package com.example.bicyclerentalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class BookRide : AppCompatActivity() {

     var sr  =  ArrayList<StoreRides>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_ride)
        val backButton: ImageView = findViewById(R.id.back)
        backButton.setOnClickListener {
            finish()
        }
        var recycler:RecyclerView=findViewById<RecyclerView>(R.id.book_ride_recycler)
        recycler.layoutManager=LinearLayoutManager(this)

        sr =  arrayListOf()
        sr.add(StoreRides("1" , "Block-33" , "15","Available"))
        sr.add(StoreRides("2" , "Block-37" , "20","Available"))
        sr.add(StoreRides("3" , "Main-Park" , "20","Available"))
        sr.add(StoreRides("4" , "Gh1-Park" , "20","Available"))
        sr.add(StoreRides("5" , "N-K Canteen" , "20","Available"))
        sr.add(StoreRides("6" , "Uni-Hotel" , "20","Available"))


        var adapter =  BookRideAdapter(this , sr)
        recycler.adapter =  adapter

    }
}