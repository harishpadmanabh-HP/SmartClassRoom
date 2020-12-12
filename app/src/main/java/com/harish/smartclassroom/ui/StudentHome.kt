package com.harish.smartclassroom.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.harish.smartclassroom.MainActivity
import com.harish.smartclassroom.R
import com.harish.smartclassroom.data.AppData
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

    fun onLogout(view: View) {
        MaterialAlertDialogBuilder(this)
            .setTitle("LOGOUT")
            .setMessage("Are you sure that you want to logout?")
            .setCancelable(false)

            .setNegativeButton("Dismiss") { dialog, which ->
                // Respond to negative button press
                dialog.dismiss()
            }
            .setPositiveButton("LOG OUT") { dialog, which ->

                AppData.init(this).logout()
                startActivity(
                    Intent(
                    this, MainActivity::class.java
                )
                )

            }
            .show()
    }
}