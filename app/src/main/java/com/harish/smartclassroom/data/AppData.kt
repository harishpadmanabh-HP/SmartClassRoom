package com.harish.smartclassroom.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.harish.smartclassroom.data.models.LoginResponse
import com.harish.smartclassroom.data.models.StudentDetails
import java.util.concurrent.TimeUnit

object AppData {

    private var preferences: SharedPreferences? = null

    fun init(context: Context): AppData {
        preferences = context.getSharedPreferences("sync_manager_v2", Context.MODE_PRIVATE)
        return this
    }

    fun setBatchId(batchId : String){
        val editor = preferences?.edit()
        editor?.putString("batchID",batchId )
        editor?.apply()
    }

    fun getBatchId():String{
       var batchId = ""
       preferences?.let {
           batchId = it.getString("batchID","").toString()
       }
        return batchId
    }

    fun setSemester(sem:String){
        val editor = preferences?.edit()
        editor?.putString("sem",sem )
        editor?.apply()
    }

    fun getSemester():String{
        var sem = ""
        preferences?.let { it ->
            sem = it.getString("sem","").toString()
        }
        return sem
    }

    fun setLoggedin(status:Boolean){
        val editor = preferences?.edit()
        editor?.putBoolean("loggedin",status )
        editor?.apply()

    }

    fun hasLoggedin():Boolean{
        var status =false
         preferences?.let { it ->
            status = it.getBoolean("loggedin",false)
        }
        return status
    }

    fun setStudentDict(data:LoginResponse.StudentData){
        val editor = preferences?.edit()
        editor?.putString("studentdict", Gson().toJson(data) )
        editor?.apply()
    }

    fun getStudentDict():LoginResponse.StudentData?{
        var studentData : LoginResponse.StudentData? = null
        preferences?.let {
            studentData = Gson().fromJson(it.getString("studentdict",""),LoginResponse.StudentData::class.java)
        }
        return studentData
    }

    fun setStudentDetails(data:StudentDetails){
        val editor = preferences?.edit()
        editor?.putString("studentdetails", Gson().toJson(data) )
        editor?.apply()
    }

    fun getStudentDetails():StudentDetails?{
        var studentData : StudentDetails? = null
        preferences?.let {
            studentData = Gson().fromJson(it.getString("studentdetails",""),StudentDetails::class.java)
        }
        return studentData
    }

}