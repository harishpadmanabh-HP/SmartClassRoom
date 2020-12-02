package com.harish.smartclassroom.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.harish.smartclassroom.data.models.AssignmentListResponse
import com.harish.smartclassroom.data.repositories.StudentRepository

class HomeViewModel( var application: Application) : ViewModel() {

    val repository =StudentRepository(application)
    var assignments : MutableLiveData<List<AssignmentListResponse.AssignmentDetail>> = MutableLiveData()
    var events : MutableLiveData<String> = MutableLiveData()

    fun getAssignments(sem:String, batchId : String)= repository.getAssignmentList(sem,batchId,onResponse = {
        status, message, response ->
        if(status) assignments.postValue(response?.assignmentDetails)
        else events.postValue(message)
    })



}

open class HomeViewModelFactory(val app: Application) : ViewModelProvider.AndroidViewModelFactory(app){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(app) as T
    }
}