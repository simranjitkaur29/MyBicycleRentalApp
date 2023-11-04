package com.example.bicyclerentalapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator  // Add this line


class RideHistory : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_history)
        viewPager=findViewById(R.id.viewPager)
        tabLayout=findViewById(R.id.tabLayout)
        val backButton: ImageView = findViewById(R.id.back)
        backButton.setOnClickListener {
            finish()
        }
        val adapter = ViewPagerAdapter(this@RideHistory)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "ONGOING"
                1 -> tab.text = "PREVIOUS"
                2 -> tab.text = "CANCELLED"
            }
        }.attach()
    }

}
