package com.harish.smartclassroom.data

import android.content.Context
import android.content.SharedPreferences
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

}