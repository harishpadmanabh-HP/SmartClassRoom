package com.harish.smartclassroom.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.harish.smartclassroom.data.models.BatchResponse
import com.harish.smartclassroom.data.repositories.StudentRepository

class OnBoardingViewModel( var application: Application) : ViewModel() {

    val repository = StudentRepository(application)
    val events : MutableLiveData<String> = MutableLiveData()
    val batches : MutableLiveData<BatchResponse> = MutableLiveData()

    fun getBatches()=repository.getBatches(onResponse = {status, message, response ->
       if(status) batches.postValue(response)
        else events.postValue(message)
    })


}

open class OnbaordingViewModelFactory(val app: Application) : ViewModelProvider.AndroidViewModelFactory(app){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OnBoardingViewModel(app) as T
    }
}