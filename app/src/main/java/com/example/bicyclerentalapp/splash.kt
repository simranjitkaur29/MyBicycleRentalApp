package com.example.bicyclerentalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var image: ImageView =findViewById(R.id.SplashScreenImage)
        image.alpha = 0f
        image.animate().setDuration(1500).alpha(1f).withEndAction{
            val intent = Intent(this , logIn::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out)
            finish()

        }
    }
}