package com.harish.smartclassroom.data.repositories

import android.app.Application
import android.content.Context
import com.harish.smartclassroom.R
import com.harish.smartclassroom.data.Apis
import com.harish.smartclassroom.data.models.BatchResponse
import com.harish.smartclassroom.data.models.LoginResponse
import com.harish.smartclassroom.data.models.RegistrationResponse
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
    fun getLoginStatus(regno:String,password:String,onResponse: (status: Boolean, message: String?, response: LoginResponse?) -> Unit)
    {
        api.getLoginStatus(regno,password).enqueue(object:Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                if(!Utils.hasInternetConnection(context.applicationContext)){
                    onResponse(false,context.getString(R.string.offline),null)
                }else{
                    onResponse(false,context.getString(R.string.server_error),null)
                }
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    onResponse(true,"",response.body())
                }else{
                    onResponse(false,context.getString(R.string.server_error),null)
                }
            }
        })
    }
    fun getRegistrationStatus(name:String,email:String,batch:String,semester:String,password:String,regno:String,onResponse: (status: Boolean, message: String?, response: RegistrationResponse?) -> Unit)
    {
  api.getRegistrationStatus(name, email, batch, semester, password, regno).enqueue(object:Callback<RegistrationResponse>
  {
      override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
          if(!Utils.hasInternetConnection(context.applicationContext)){
              onResponse(false,context.getString(R.string.offline),null)
          }else{
              onResponse(false,context.getString(R.string.server_error),null)
          }
      }

      override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
          if(response.isSuccessful){
              onResponse(true,"",response.body())
          }else{
              onResponse(false,context.getString(R.string.server_error),null)
          }
      }
  }
  )

}
}