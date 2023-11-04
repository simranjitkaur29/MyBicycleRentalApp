package com.example.bicyclerentalapp
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bicyclerentalapp.databinding.ActivityLogInBinding

class logIn : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var databaseHelper: DatabaseHelper
    lateinit var email  : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelper(this)
        // Retrieve the username from the intent




        binding.loginButton.setOnClickListener {
            val username = binding.loginUsername.text.toString()
            val password = binding.loginPassword.text.toString()
            email=intent.getStringExtra("Signemail").toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            } else {
                val checkCredentials = databaseHelper.checkUsernamePassword(username, password)
                if (checkCredentials) {
                    Toast.makeText(this, "Login Successfully!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@logIn, home::class.java)
                    intent.putExtra("USERNAME", username)
                    intent.putExtra("USERCODE",password)
                    intent.putExtra("UserEmail",email)

                    startActivity(intent)
//
                } else {
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.signupRedirectText.setOnClickListener {
            val intent = Intent(this, signUp::class.java)
            startActivity(intent)
        }
    }
}
