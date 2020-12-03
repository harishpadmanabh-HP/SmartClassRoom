package com.harish.smartclassroom.data.models


import com.google.gson.annotations.SerializedName

data class QuizResponse(
    @SerializedName("Question")
    val question: List<Question>,
    @SerializedName("Quiz_details")
    val quizDetails: List<QuizDetail>,
    @SerializedName("status")
    val status: String
) {
    data class Question(
        @SerializedName("a")
        val a: String,
        @SerializedName("b")
        val b: String,
        @SerializedName("c")
        val c: String,
        @SerializedName("Correct_answer")
        val correctAnswer: String,
        @SerializedName("d")
        val d: String,
        @SerializedName("question")
        val question: String
    ){
        var isAnswered = false

    }

    data class QuizDetail(
        @SerializedName("batch_id")
        val batchId: String,
        @SerializedName("exam_id")
        val examId: String,
        @SerializedName("exam_title")
        val examTitle: String,
        @SerializedName("faculty_id")
        val facultyId: String,
        @SerializedName("semester")
        val semester: String,
        @SerializedName("subject")
        val subject: String
    )
}