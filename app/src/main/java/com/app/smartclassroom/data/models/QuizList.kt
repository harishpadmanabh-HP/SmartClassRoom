package com.app.smartclassroom.data.models


import com.google.gson.annotations.SerializedName

data class QuizList(
    @SerializedName("exam")
    val exam: List<Exam>,
    @SerializedName("status")
    val status: Boolean
) {
    data class Exam(
        @SerializedName("batch_id")
        val batchId: String,
        @SerializedName("date")
        val date: String,
        @SerializedName("exam_id")
        val examId: String,
        @SerializedName("faculty_id")
        val facultyId: String,
        @SerializedName("semester")
        val semester: String,
        @SerializedName("subject")
        val subject: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("examtime")
        val duration :  String

    )
}