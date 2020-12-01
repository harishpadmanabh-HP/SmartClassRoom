package com.harish.smartclassroom.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.harish.smartclassroom.R
import com.harish.smartclassroom.viewmodels.OnBoardingViewModel
import com.harish.smartclassroom.viewmodels.OnbaordingViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    lateinit var viewModel : OnBoardingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val viewModelFactory = OnbaordingViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(OnBoardingViewModel::class.java)
        setupObserver()

    }

    fun registerClicked(view: View) {
        startActivity(Intent(this, Register::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
    }
    fun onLoginClick(view: View) {
        var regno=editTextRegno.text.toString()
        var password=editTextPassword.text.toString()

        if(regno.isNullOrEmpty() && password.isNullOrEmpty())
        {
            Toast.makeText(this, "Enter Details", Toast.LENGTH_SHORT).show()
        }
        else
        {
            viewModel.getLoginStatus(regno,password)
        }
    }
    fun setupObserver()
    {
        viewModel.loginstatus.observe(this@Login, Observer {
            Toast.makeText(this@Login, it.status, Toast.LENGTH_SHORT).show()
        })
        viewModel.events.observe(this@Login, Observer {
            Toast.makeText(this@Login, it, Toast.LENGTH_SHORT).show()
        })
    }
}