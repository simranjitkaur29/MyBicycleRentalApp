package com.example.bicyclerentalapp

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bicyclerentalapp.databinding.ActivitySignUpBinding

class signUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var databaseHelper: DatabaseHelper
    lateinit var textEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelper(this)
        textEmail=findViewById(R.id.signup_email)


        binding.signupButton.setOnClickListener {
            val username = binding.signupUsername.text.toString()
            val password = binding.signupPassword.text.toString()
            val email=textEmail.text.toString()
            val confirmPassword = binding.signupConfirm.text.toString()

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            } else {
                if (password == confirmPassword) {
                    val checkUsername = databaseHelper.checkUsername(username)
                    if (!checkUsername) {
                        val insert = databaseHelper.insertData(username, password)
                        if (insert) {
                            Toast.makeText(this, "Signup Successfully!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, logIn::class.java)

                            intent.putExtra("Signemail",email)

                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Signup Failed!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Username already exists! Please choose another.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Invalid Password!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.loginRedirectText.setOnClickListener {
            val intent = Intent(this, logIn::class.java)
            startActivity(intent)
        }
    }
}
