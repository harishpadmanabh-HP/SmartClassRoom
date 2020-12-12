package com.apps.smartclassroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.apps.smartclassroom.data.AppData
import com.apps.smartclassroom.ui.StudentHome
import com.apps.smartclassroom.ui.walkthrough.Walkthrough

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (AppData.init(this).getStudentDetails() == null) {

                Handler().postDelayed({
                    val intent = Intent(this, Walkthrough::class.java)
                    startActivity(intent)
                    finish()
                }, 3000)

        }else{
            val intent = Intent(this, StudentHome::class.java)
            startActivity(intent)
            finish()
        }


    }
}