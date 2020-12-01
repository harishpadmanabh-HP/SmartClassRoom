package com.harish.smartclassroom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.harish.smartclassroom.R
import kotlinx.android.synthetic.main.activity_student_home.*

class StudentHome : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)

        setupBottomNavigation()


    }

    private fun setupBottomNavigation(){
     val  navController = findNavController(R.id.nav_host_fragment_container)
        bottomNavigationView.setupWithNavController(navController)

    }
}