package com.example.bicyclerentalapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val backButton: ImageView = findViewById(R.id.back)
        backButton.setOnClickListener {
            finish()
        }
        // Retrieve data from intent
        val username = intent.getStringExtra("user")
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")

        // Set data to TextViews
        val usernameTextView = findViewById<TextView>(R.id.profile_username)
        val emailTextView = findViewById<TextView>(R.id.profile_email)
        val passwordTextView = findViewById<TextView>(R.id.profile_password)

        usernameTextView.text = username
        emailTextView.text = email
        passwordTextView.text = password

        // Set up Toolbar
//        val toolbar = findViewById<Toolbar>(R.id.profile_toolbar)
//        toolbar.title = "My Profile"
//        toolbar.setTitleTextColor(resources.getColor(android.R.color.white))
//        setSupportActionBar(toolbar)

    }
}