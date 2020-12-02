package com.harish.smartclassroom.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.harish.smartclassroom.R
import com.harish.smartclassroom.data.models.BatchResponse
import com.harish.smartclassroom.viewmodels.OnBoardingViewModel
import com.harish.smartclassroom.viewmodels.OnbaordingViewModelFactory
import kotlinx.android.synthetic.main.activity_register.*


class Register : AppCompatActivity() {

    lateinit var viewModel : OnBoardingViewModel
    lateinit var batchList : List<String>
    var selectedBatch : String ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val viewModelFactory = OnbaordingViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(OnBoardingViewModel::class.java)

        viewModel.getBatches()
        setupObservers()

        editTextBatch.setOnItemClickListener { adapterView, view, position, _ ->
            selectedBatch = batchList[position]
            Toast.makeText(this, selectedBatch, Toast.LENGTH_SHORT).show()
          //

        }
    }

    fun onLoginClick(view: View) {
        startActivity(Intent(this, Login::class.java))
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right)

    }

    fun setupObservers(){
        viewModel.apply{
            batches.observe(this@Register, Observer {
                batchList=it.batchDetails.map {
                    it.batchName
                }
               setUpBatchDropDown(batchList)
            })
            events.observe(this@Register, Observer {
                Toast.makeText(this@Register, it, Toast.LENGTH_SHORT).show()
            })
        }

    }
fun setupRegistrationObservers(){
    viewModel.apply {
        registrationstatus.observe(this@Register, Observer {
            Toast.makeText(this@Register,it.status, Toast.LENGTH_SHORT).show()
        })
        events.observe(this@Register, Observer {
            Toast.makeText(this@Register,it, Toast.LENGTH_SHORT).show()
        })
    }
}
    fun setUpBatchDropDown(batchList:List<String>){
        val d_adapter  = ArrayAdapter(
            this@Register,
            android.R.layout.select_dialog_item,
            batchList
        )
        editTextBatch.setThreshold(1) //will start working from first character

        editTextBatch.setAdapter(d_adapter) //setting the adapter data into the AutoCompleteTextView

        editTextBatch.setTextColor(Color.BLACK)
    }

    fun signupClick(view: View) {
        var name=regeditTextName.text.toString()
        var email=regeditTextEmail.text.toString()
        var semester="S"+regeditTextSem.text.toString()
        var password=regeditTextPassword.text.toString()
        var regno=regeditTextRegno.text.toString()

        if(name.isNullOrEmpty())
        {
            Toast.makeText(this, "Name is empty", Toast.LENGTH_SHORT).show()
        }
        else if(email.isNullOrEmpty())
        {
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show()
        }
        else if(selectedBatch.isNullOrEmpty())
        {
            Toast.makeText(this, "Select Batch", Toast.LENGTH_SHORT).show()
        }
        else if(semester.isNullOrEmpty())
        {
            Toast.makeText(this, "Select semester", Toast.LENGTH_SHORT).show()
        }
        else if(password.isNullOrEmpty())
      {
          Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
}
        else if(regno.isNullOrEmpty())
        {
            Toast.makeText(this, "Enter regno", Toast.LENGTH_SHORT).show()
        }
        else
        {
            viewModel.getRegistrationStatus(name,email, selectedBatch.toString(), semester, password, regno)
        }
    }


}