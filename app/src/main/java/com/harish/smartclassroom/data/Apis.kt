package com.harish.smartclassroom.data

import com.harish.smartclassroom.data.models.*
import com.harish.smartclassroom.util.BASE_URL
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit
import javax.security.auth.Subject


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


    @Multipart
    @POST("assignment_submit.php")
    fun addAssignment(@Part("batch_id") batchid: RequestBody,
                      @Part("assign_id") assignmentID : RequestBody,
                      @Part("semester") semester : RequestBody,
                      @Part("stud_id") stud_id : RequestBody,
                      @Part("faculty_id") faculty_id : RequestBody,
                      @Part("subject") subject : RequestBody,
                      @Part image: MultipartBody.Part,
                      ):Call<AddAssignmentResponse>


    @GET("view_note.php?")
    fun getNotesList(@Query("semester") semester: String,
                     @Query("batch_id") batchID: String,
                     @Query("subject") subject: String
                     ):Call<NotesResponse>

    @GET("view_quiz.php?")
    fun getQuiz(@Query("exam_id") exam_id : String):Call<QuizResponse>


    companion object {
        operator fun invoke(): Apis {
            var logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient =
                    OkHttpClient.Builder().addInterceptor(logger).build()

            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build()


            return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .build()
                    .create(Apis::class.java)
        }
    }

}