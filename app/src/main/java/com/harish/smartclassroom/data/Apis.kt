package com.harish.smartclassroom.data

import com.harish.smartclassroom.data.models.AssignmentListResponse
import com.harish.smartclassroom.data.models.BatchResponse
import com.harish.smartclassroom.data.models.LoginResponse
import com.harish.smartclassroom.data.models.RegistrationResponse
import com.harish.smartclassroom.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface Apis {

    @GET("view_batch.php")
    fun getBatches(): Call<BatchResponse>

     @GET("student_login.php?")
      fun getLoginStatus(@Query("regno")registerno:String,@Query("password")password:String): Call<LoginResponse>

    //student_reg.php?name=Athira&email=athira@gmail.com&batch=Computer%20Science&semester=S1&password=Athira@123&regno=CS001
    @GET("student_reg.php?")
    fun getRegistrationStatus(@Query("name")name:String,
                              @Query("email")email:String,
                              @Query("batch")batch:String,
                              @Query("semester")semester:String,
                              @Query("password")password:String,
                              @Query("regno")regno:String):Call<RegistrationResponse>

    @GET("view_assignment.php?")
    fun getAssignmentList(@Query("semester") semester: String,
                          @Query("batch_id") batch: String):Call<AssignmentListResponse>


    companion object {
        operator fun invoke(): Apis {
            var logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            val client: OkHttpClient =
                    OkHttpClient.Builder().addInterceptor(logger).build()

            return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl(BASE_URL)
                    .build()
                    .create(Apis::class.java)
        }
    }

}