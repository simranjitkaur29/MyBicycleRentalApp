package com.example.bicyclerentalapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import com.example.bicyclerentalapp.DatabaseHelper
import com.example.bicyclerentalapp.R




class ChangePassword : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    lateinit var uco  : String
    lateinit var una  : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        uco = intent.getStringExtra("uc").toString()

        una  = intent.getStringExtra("un").toString()



        Toast.makeText(this, una, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, uco, Toast.LENGTH_SHORT).show()

        Log.d("ChangePassword", "Username from Intent: $una")
        Log.d("ChangePassword", "pass from Intent: $uco")




        databaseHelper = DatabaseHelper(this)


        val oldPasswordEditText: EditText = findViewById(R.id.oldPasswordEditText)
        val newPasswordEditText: EditText = findViewById(R.id.newPasswordEditText)
        val changePasswordButton: Button = findViewById(R.id.changePasswordButton)

        changePasswordButton.setOnClickListener {
            val oldPassword = oldPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()

            // Retrieve username from intent with a default value to handle null case

            Log.d("ChangePassword", "Old Password: $uco, New Password: $newPassword, Username: $una")

            if (oldPassword.isNotBlank() && newPassword.isNotBlank() && una!!.isNotEmpty()) {



                databaseHelper.updatePasswordAsync(una, newPassword) { updateSuccess ->
                    runOnUiThread {
                        Log.d("ChangePassword", "Password update result: $updateSuccess")

                        if (updateSuccess) {
                            Log.d("ChangePassword", "Password updated successfully")
                            Toast.makeText(
                                this,
                                "Password updated successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Log.d("ChangePassword", "Failed to update password")
                            Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                // Handle the case where passwords are empty or username is empty
                Toast.makeText(
                    this,
                    "Please enter both old and new passwords",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("ChangePassword", "Condition not met. Password change aborted.")
            }
        }
    }
}
