package com.apps.smartclassroom.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apps.smartclassroom.R
import com.apps.smartclassroom.data.Apis
import com.apps.smartclassroom.data.models.*
import com.apps.smartclassroom.util.Utils
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
    })
 }


    fun getAssignmentList(sem:String,batch: String,onResponse: (status: Boolean, message: String?, response: AssignmentListResponse?) -> Unit){
        api.getAssignmentList(sem,batch).enqueue(object:Callback<AssignmentListResponse>{
            override fun onFailure(call: Call<AssignmentListResponse>, t: Throwable) {
                if(!Utils.hasInternetConnection(context.applicationContext)){
                    onResponse(false,context.getString(R.string.offline),null)
                }else{
                    onResponse(false,context.getString(R.string.server_error),null)
                }
            }

            override fun onResponse(call: Call<AssignmentListResponse>, response: Response<AssignmentListResponse>) {
                if(response.isSuccessful){
                    onResponse(true,"",response.body())
                }else{
                    onResponse(false,context.getString(R.string.server_error),null)
                }
            }
        })
    }

    fun getNotes(semester: String ,
                 batchId: String,
                 subject: String,
                 onResponse: (status: Boolean, message: String?, response: NotesResponse?) -> Unit
                 ){
        api.getNotesList(semester,batchId,subject).enqueue(object : Callback<NotesResponse>{
            override fun onFailure(call: Call<NotesResponse>, t: Throwable) {
                if(!Utils.hasInternetConnection(context.applicationContext)){
                    onResponse(false,context.getString(R.string.offline),null)
                }else{
                    onResponse(false,context.getString(R.string.server_error),null)
                }
            }

            override fun onResponse(call: Call<NotesResponse>, response: Response<NotesResponse>) {
                if(response.isSuccessful){
                    onResponse(true,"",response.body())
                }else{
                    onResponse(false,context.getString(R.string.server_error),null)
                }
            }
        })
    }


    fun getQuiz(examId : String, onResponse: (status: Boolean, message: String?, response: QuizResponse?) -> Unit){
        api.getQuiz(examId).enqueue(object : Callback<QuizResponse>{
            override fun onFailure(call: Call<QuizResponse>, t: Throwable) {
                if(!Utils.hasInternetConnection(context.applicationContext)){
                    onResponse(false,context.getString(R.string.offline),null)
                }else{
                    onResponse(false,context.getString(R.string.server_error),null)
                }
            }

            override fun onResponse(call: Call<QuizResponse>, response: Response<QuizResponse>) {
                if(response.isSuccessful){
                    onResponse(true,"",response.body())
                }else{
                    onResponse(false,context.getString(R.string.server_error),null)
                }
            }
        })
    }

    fun getQuizList(
        semester: String,
        batchId: String,
        subject: String,
        onResponse: (status: Boolean, message: String?, response: QuizList?) -> Unit
    ){
        api.getQuizList(semester, batchId, subject).enqueue(object :Callback<QuizList>{
            override fun onFailure(call: Call<QuizList>, t: Throwable) {
                if(!Utils.hasInternetConnection(context.applicationContext)){
                    onResponse(false,context.getString(R.string.offline),null)
                }else{
                    onResponse(false,context.getString(R.string.server_error),null)
                }
            }

            override fun onResponse(call: Call<QuizList>, response: Response<QuizList>) {
                if(response.isSuccessful){
                    onResponse(true,"",response.body())
                }else{
                    onResponse(false,context.getString(R.string.server_error),null)
                }
            }
        })
    }

    fun getStudentDetails(studID:String,onResponse: (status: Boolean, message: String?, response: StudentDetails?) -> Unit){
        api.getStudentDetails(studID).enqueue(object :Callback<StudentDetails>{
            override fun onFailure(call: Call<StudentDetails>, t: Throwable) {
                if(!Utils.hasInternetConnection(context.applicationContext)){
                    onResponse(false,context.getString(R.string.offline),null)
                }else{
                    onResponse(false,context.getString(R.string.server_error),null)
                }
            }

            override fun onResponse(
                call: Call<StudentDetails>,
                response: Response<StudentDetails>
            ) {
                if(response.isSuccessful){
                    onResponse(true,"",response.body())
                }else{
                    onResponse(false,context.getString(R.string.server_error),null)
                }
            }
        })
    }

    fun submitExam(examId: String,studID: String,correctAnswers:String):MutableLiveData<Boolean>{
        var status = MutableLiveData<Boolean>()
        api.submitQuiz(examId,studID,correctAnswers).enqueue(object : Callback<SubmitQuizModel>{
            override fun onFailure(call: Call<SubmitQuizModel>, t: Throwable) {
               status.postValue(false)
            }

            override fun onResponse(
                call: Call<SubmitQuizModel>,
                response: Response<SubmitQuizModel>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status)
                        status.postValue(true)
                    else
                        status.postValue(false)


                }else{
                    status.postValue(false)

                }
            }

        })

        return status
    }


}