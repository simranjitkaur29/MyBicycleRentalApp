package com.example.bicyclerentalapp


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class home : AppCompatActivity() {
    // Create an instance of DatabaseHelper
    private lateinit var databaseHelper: DatabaseHelper
    lateinit var usercode  : String
    lateinit var username  : String
    lateinit var emaill:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val backButton: ImageView = findViewById(R.id.back)
        backButton.setOnClickListener {
            finish()
        }
        val menuImageView: ImageView = findViewById(R.id.menu)
        menuImageView.setOnClickListener {
            showPopupMenu(menuImageView)
        }

        val profile=findViewById<CardView>(R.id.baclog)
        val home:CardView=findViewById(R.id.Registration)
        home.setOnClickListener {
            val intent=Intent(this,BookRide::class.java)
            startActivity(intent)
        }
        val rideHis:CardView=findViewById(R.id.ride)
        rideHis.setOnClickListener {
            val intent=Intent(this,RideHistory::class.java)
            startActivity(intent)
        }
        val currentAct=findViewById<CardView>(R.id.current)
        currentAct.setOnClickListener {
            val intent=Intent(this,CurrentRides::class.java)
            startActivity(intent)
        }
        val infopage=findViewById<CardView>(R.id.info)
        infopage.setOnClickListener {
            val intent=Intent(this,info::class.java)
            startActivity(intent)
        }
        val logout=findViewById<CardView>(R.id.logout)
        logout.setOnClickListener {
            val intent = Intent(this, logIn::class.java)

            // Clear the back stack and start a new task
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home2 -> {
                    // Handle click on Home
                    val intent=Intent(this,home::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bell2 -> {
                    // Handle click on History
                   val intent=Intent(this,RideHistory::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.placeholder -> {
                    // Handle click on Profile
                    val intent = Intent(this, Profile::class.java)
                    intent.putExtra("user", username)
                    intent.putExtra("email", emaill)
                    intent.putExtra("password", usercode)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.settings2 -> {
                    // Handle click on Info
                    val intent=Intent(this,info::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }


        usercode  = intent.getStringExtra("USERCODE").toString()
        username = intent.getStringExtra("USERNAME").toString()
        emaill=intent.getStringExtra("UserEmail").toString()
        profile.setOnClickListener{
            val intent=Intent(this,Profile::class.java )
            intent.putExtra("user",username)
            intent.putExtra("email",emaill)
            intent.putExtra("password",usercode)
            startActivity(intent)
        }
        Toast.makeText(this, usercode, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, username, Toast.LENGTH_SHORT).show()


        // Initialize the DatabaseHelper instance
        databaseHelper = DatabaseHelper(this)

        // Set the username to the EditText
        val editText: TextView = findViewById(R.id.editText)
        editText.setText(username)
       // supportActionBar?.title = "Home"

       // toolbar.setTitleTextColor(Color.WHITE)


    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.home_menu, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_change_password -> {
                    val uco = usercode
                    val una = username
                    val intent = Intent(this, ChangePassword::class.java)
                    intent.putExtra(uco, "uc")
                    intent.putExtra(una, "un")
                    Toast.makeText(this, uco, Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    true
                }
                R.id.menu_logout -> {
                    onLogoutClicked()
                    true
                }
                else -> false
            }
        }

        popup.show()
    }

    // Remove the parameter from the method since it's not being used
    private fun onChangePasswordClicked() {
        // Start the ChangePassword activity

    }

    private fun onLogoutClicked() {
        val intent = Intent(this, logIn::class.java)

        // Clear the back stack and start a new task
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
        finish()
    }

}

