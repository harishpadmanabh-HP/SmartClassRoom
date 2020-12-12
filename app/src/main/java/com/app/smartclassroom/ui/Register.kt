package com.app.smartclassroom.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.app.smartclassroom.R
import com.app.smartclassroom.data.AppData
import com.app.smartclassroom.data.models.BatchResponse
import com.app.smartclassroom.viewmodels.OnBoardingViewModel
import com.app.smartclassroom.viewmodels.OnbaordingViewModelFactory
import kotlinx.android.synthetic.main.activity_register.*


class Register : AppCompatActivity() {

    lateinit var viewModel : OnBoardingViewModel
    lateinit var batchList : List<String>
    var selectedBatch : String ?=null
    val semList:List<String> = listOf("S1","S2","S3","S4","S5","S6","S7","S8")
    var selectedSem : String? = null
    lateinit var appData:AppData
    var batchResponse : BatchResponse ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val viewModelFactory = OnbaordingViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(OnBoardingViewModel::class.java)

        appData = AppData.init(this)

        viewModel.getBatches()
        setupObservers()

        editTextBatch.setOnItemClickListener { adapterView, view, position, _ ->
            selectedBatch = batchList[position]
            batchResponse?.batchDetails?.get(position)?.batchId?.let {
                appData.setBatchId(it)
            }
        }
        regeditTextSem.setOnItemClickListener{adapterView, view, position, _ ->
            selectedSem = semList[position]
            appData.setSemester(selectedSem!!)
        }
    }

    fun onLoginClick(view: View) {
        startActivity(Intent(this, Login::class.java))
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right)

    }

    fun setupObservers(){
        viewModel.apply{
            batches.observe(this@Register, Observer {
                batchResponse =it
                batchList=it.batchDetails.map {
                    it.batchName
                }
                setUpBatchDropDown(batchList)
                setupSemDropDown()
            })
            events.observe(this@Register, Observer {
                Toast.makeText(this@Register, it, Toast.LENGTH_SHORT).show()
            })
            registrationstatus.observe(this@Register, Observer {

                if(it.status.equals("success")){
                    appData.setLoggedin(true)
                    pb_load.visibility = View.INVISIBLE
                    startActivity(Intent(this@Register,Login::class.java))
                }else{
                    pb_load.visibility = View.INVISIBLE
                    Snackbar.make(rl_root,"Registration failed", Snackbar.LENGTH_LONG).show()
                }

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

    fun setupSemDropDown(){
        val d_adapter  = ArrayAdapter(
                this@Register,
                android.R.layout.select_dialog_item,
                semList
        )
        regeditTextSem.setThreshold(1) //will start working from first character

        regeditTextSem.setAdapter(d_adapter) //setting the adapter data into the AutoCompleteTextView

        regeditTextSem.setTextColor(Color.BLACK)
    }

    fun onRegisterClick(view: View) {
        pb_load.visibility = View.VISIBLE
        var name=regeditTextName.text.toString()
        var email=regeditTextEmail.text.toString()
        var password=regeditTextPassword.text.toString()
        var regno=regeditTextRegno.text.toString()

        if(name.isNullOrEmpty() ||
                email.isNullOrEmpty() ||
                selectedBatch.isNullOrEmpty()||
                selectedSem.isNullOrEmpty()||
                password.isNullOrEmpty()||
                regno.isNullOrEmpty()
                ){
            pb_load.visibility = View.INVISIBLE
            Toast.makeText(this, "Please Enter All fields", Toast.LENGTH_SHORT).show()
        }else{
            viewModel.getRegistrationStatus(name,email, selectedBatch!!, selectedSem!!,password,regno)
        }

    }


}