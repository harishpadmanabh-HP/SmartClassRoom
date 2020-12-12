package com.app.smartclassroom.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.app.smartclassroom.R
import com.app.smartclassroom.data.AppData
import com.app.smartclassroom.viewmodels.OnBoardingViewModel
import com.app.smartclassroom.viewmodels.OnbaordingViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    lateinit var viewModel : OnBoardingViewModel
    lateinit var appData : AppData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val viewModelFactory = OnbaordingViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(OnBoardingViewModel::class.java)
        appData = AppData.init(this)
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

            if(it.status.equals("Success")){
                appData.setStudentDict(it.Student_data)
                appData.setLoggedin(true)


                startActivity(Intent(this@Login,StudentHome::class.java))
            }else{
                Snackbar.make(rl_root,"Login failed",Snackbar.LENGTH_LONG).show()
            }
        })
        viewModel.events.observe(this@Login, Observer {
            Toast.makeText(this@Login, it, Toast.LENGTH_SHORT).show()
        })
    }
}