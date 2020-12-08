package com.harish.smartclassroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.harish.smartclassroom.ui.Login
import com.harish.smartclassroom.ui.walkthrough.Walkthrough

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            val intent = Intent(this, Walkthrough::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}