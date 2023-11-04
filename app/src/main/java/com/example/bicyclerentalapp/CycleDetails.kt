package com.example.bicyclerentalapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager

import android.os.Bundle
import android.util.Log
import android.widget.Button

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.Manifest
import android.location.Location
import android.location.Geocoder



class CycleDetails : AppCompatActivity() {
    private lateinit var locationManager: LocationManager
    private lateinit var locationEditText: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cycle_details)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationEditText = findViewById(R.id.Location_edit_text)
        locationEditText.setOnClickListener {
            // Request the user's permission to access their location
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }

        val backButton: ImageView = findViewById(R.id.back)
        backButton.setOnClickListener {
            finish()
        }
        val n = intent.getStringExtra("name").toString()
        val m = intent.getStringExtra("price").toString()
        val s = intent.getStringExtra("stop").toString()

        val takeRide: Button = findViewById(R.id.TakeRide)
        val user: EditText = findViewById(R.id.user_edit_text)
        user.setText("Bike No. " + intent.getStringExtra("name").toString())
        val price: EditText = findViewById(R.id.price_edit_text)
        price.setText(intent.getStringExtra("price").toString())
        val status: EditText = findViewById(R.id.status_edit_text)
        status.setText(intent.getStringExtra("status").toString())
        val stop: EditText = findViewById(R.id.stop_edit_text)
        stop.setText(intent.getStringExtra("stop").toString())
        takeRide.setOnClickListener {
            val intent = Intent(this, TakeRide::class.java)
            intent.putExtra("bikenum", n)
            intent.putExtra("money", m)
            intent.putExtra("stop", s)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // The user has granted permission to access their location

            // Check for location permission again before accessing location updates
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val locationListener = LocationListener { location: Location ->
                    // Get the user's current location
                    val latitude = location.latitude
                    val longitude = location.longitude

                    // Use geocoding to get the address from latitude and longitude
                    val geocoder = Geocoder(this)
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                    if (addresses != null) {
                        if (addresses.isNotEmpty()) {
                            val address = addresses[0]
                            val locationText = "${address.thoroughfare}, ${address.subLocality}, ${address.locality}, ${address.countryName}"

                            // Update the user's location in the Location_edit_text on the UI thread
                            runOnUiThread {
                                locationEditText.text = locationText
                            }
                        }
                    }
                }

                // Register the location listener
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000,
                    0f,
                    locationListener
                )
            }
        }
    }
}