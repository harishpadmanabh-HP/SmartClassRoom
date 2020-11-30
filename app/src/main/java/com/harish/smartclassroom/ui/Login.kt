package com.harish.smartclassroom.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.harish.smartclassroom.R

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onRegisterClick(view: View) {
        startActivity(Intent(this, Register::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay)

    }
}