package com.apps.smartclassroom.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apps.smartclassroom.data.models.*
import com.apps.smartclassroom.data.repositories.StudentRepository

class HomeViewModel(var application: Application) : ViewModel() {

    val repository = StudentRepository(application)
    var assignments: MutableLiveData<List<AssignmentListResponse.AssignmentDetail>> =
        MutableLiveData()
    var events: MutableLiveData<String> = MutableLiveData()
    var notes: MutableLiveData<List<NotesResponse.StudyMaterial>> = MutableLiveData()
    val quiz: MutableLiveData<QuizResponse> = MutableLiveData()
    val exams: MutableLiveData<QuizList> = MutableLiveData()
    val studentDetails: MutableLiveData<StudentDetails> = MutableLiveData()

    fun getAssignments(sem: String, batchId: String) =
        repository.getAssignmentList(sem, batchId, onResponse = { status, message, response ->
            if (status) assignments.postValue(response?.assignmentDetails)
            else events.postValue(message)
        })

    fun getNotes(sem: String, batchId: String, subject: String) =
        repository.getNotes(sem, batchId, subject, onResponse = { status, message, response ->
            if (status) notes.postValue(response?.studyMaterial)
            else events.postValue(message)
        })

    fun getQuiz(examId: String) =
        repository.getQuiz(examId, onResponse = { status, message, response ->
            if (status)
                quiz.postValue(response)
            else
                events.postValue(message)
        })

    fun getQuizList(sem: String, batchId: String, subject: String) =
        repository.getQuizList(sem, batchId, subject, onResponse = { status, message, response ->
            if (status)
                exams.postValue(response)
            else
                events.postValue(message)
        })


    fun getStudentDetails(
        studId: String,
        onSuccess: (response: StudentDetails?) -> Unit,
        onFailure: (error: String?) -> Unit
    ) = repository.getStudentDetails(studId,
        onResponse = { status, message, response ->
            if (status) {
                onSuccess(response)
            } else {
                // state.postValue(Enums.PageState.ERROR)
                if (message != null) {
                    onFailure(message)
                }
            }
        })

    fun submitQuiz(examId: String, studID: String, correctAnswers: String):MutableLiveData<Boolean> =
        repository.submitExam(examId, studID, correctAnswers)



}

open class HomeViewModelFactory(val app: Application) :
    ViewModelProvider.AndroidViewModelFactory(app) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(app) as T
    }
}