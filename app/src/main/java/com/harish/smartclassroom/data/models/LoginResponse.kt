package com.harish.smartclassroom.data.models

data class LoginResponse(
    val Student_data: StudentData,
    val status: String
) {
    data class StudentData(
        val batch: String,
        val batch_id: String,
        val email: String,
        val name: String,
        val password: String,
        val regno: String,
        val semester: String,
        val stud_id: String
    )
}