package com.harish.smartclassroom.data.repositories

import android.app.Application
import android.content.Context
import com.harish.smartclassroom.R
import com.harish.smartclassroom.data.Apis
import com.harish.smartclassroom.data.models.BatchResponse
import com.harish.smartclassroom.util.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentRepository(val context: Application) {

private   val api = Apis()

    fun getBatches(onResponse: (status: Boolean, message: String?, response: BatchResponse?) -> Unit){
      api.getBatches().enqueue(object :Callback<BatchResponse>{
          override fun onFailure(call: Call<BatchResponse>, t: Throwable) {
            if(!Utils.hasInternetConnection(context.applicationContext)){
                onResponse(false,context.getString(R.string.offline),null)
            }else{
                onResponse(false,context.getString(R.string.server_error),null)
            }
          }

          override fun onResponse(call: Call<BatchResponse>, response: Response<BatchResponse>) {
              if(response.isSuccessful){
                  onResponse(true,"",response.body())
              }else{
                  onResponse(false,context.getString(R.string.server_error),null)
              }
          }
      })
    }

}