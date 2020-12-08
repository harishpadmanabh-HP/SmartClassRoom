package com.harish.smartclassroom.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.harish.smartclassroom.data.AppData
import com.harish.smartclassroom.data.models.BatchResponse
import com.harish.smartclassroom.data.models.LoginResponse
import com.harish.smartclassroom.data.models.RegistrationResponse
import com.harish.smartclassroom.data.repositories.StudentRepository
import kotlinx.coroutines.launch

class OnBoardingViewModel(var application: Application) : ViewModel() {

    val repository = StudentRepository(application)
    val events: MutableLiveData<String> = MutableLiveData()
    val batches: MutableLiveData<BatchResponse> = MutableLiveData()
    val loginstatus: MutableLiveData<LoginResponse> = MutableLiveData()
    val registrationstatus: MutableLiveData<RegistrationResponse> = MutableLiveData()

    fun getBatches() = repository.getBatches(onResponse = { status, message, response ->
        if (status) batches.postValue(response)
        else events.postValue(message)
    })

    fun getLoginStatus(regno: String, password: String) =
        repository.getLoginStatus(regno, password, onResponse = { status, message, response ->
            if (status) {
                loginstatus.postValue(response)

                viewModelScope.launch {
                    response?.Student_data?.stud_id?.let {
                        repository.getStudentDetails(it, onResponse = { status, message, response ->
                            if (status) {
                                AppData.init(application.applicationContext)
                                    .setStudentDetails(response!!)
                                Log.e("Student", "Details updated")
                            } else {
                                events.postValue("Cant update Student details")
                            }
                        })

                    }
                }

            } else events.postValue(message)

        })

    fun getRegistrationStatus(
        name: String,
        email: String,
        batch: String,
        semester: String,
        password: String,
        regno: String
    ) = repository.getRegistrationStatus(
        name,
        email,
        batch,
        semester,
        password,
        regno,
        onResponse = { status, message, response ->
            if (status) registrationstatus.postValue(response)
            else events.postValue(message)

        })

}

open class OnbaordingViewModelFactory(val app: Application) :
    ViewModelProvider.AndroidViewModelFactory(app) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OnBoardingViewModel(app) as T
    }
}