package com.app.smartclassroom.data.models


import com.google.gson.annotations.SerializedName

data class StudentDetails(
    @SerializedName("Assignment")
    val assignment: List<Assignment>,
    @SerializedName("Exam")
    val exam: List<Exam>,
    @SerializedName("Note")
    val note: List<Note>,
    @SerializedName("status")
    val status: String,
    @SerializedName("Student_details")
    val studentDetails: List<StudentDetail>
) {
    data class Assignment(
        @SerializedName("assign_id")
        val assignId: String,
        @SerializedName("batch_id")
        val batchId: String,
        @SerializedName("faculty_id")
        val facultyId: String,
        @SerializedName("semester")
        val semester: String,
        @SerializedName("subject")
        val subject: String,
        @SerializedName("submittion_date")
        val submittionDate: String,
        @SerializedName("topic")
        val topic: String
    )

    data class Exam(
        @SerializedName("batch_id")
        val batchId: String,
        @SerializedName("date")
        val date: String,
        @SerializedName("exam_id")
        val examId: String,
        @SerializedName("examtime")
        val examtime: String,
        @SerializedName("faculty_id")
        val facultyId: String,
        @SerializedName("semester")
        val semester: String,
        @SerializedName("subject")
        val subject: String,
        @SerializedName("title")
        val title: String
    )

    data class Note(
        @SerializedName("batch_id")
        val batchId: String,
        @SerializedName("faculty_id")
        val facultyId: String,
        @SerializedName("material_id")
        val materialId: String,
        @SerializedName("note")
        val note: String,
        @SerializedName("semester")
        val semester: String,
        @SerializedName("subject")
        val subject: String,
        @SerializedName("title")
        val title: String
    )

    data class StudentDetail(
        @SerializedName("batch")
        val batch: String,
        @SerializedName("batch_id")
        val batchId: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("pending_assignment")
        val pendingAssignment: Int,
        @SerializedName("pending_exam")
        val pendingExam: Int,
        @SerializedName("reg_no")
        val regNo: String,
        @SerializedName("semester")
        val semester: String,
        @SerializedName("stud_id")
        val studId: String,
        @SerializedName("submitted_assignment")
        val submittedAssignment: String,
        @SerializedName("submitted_exam")
        val submittedExam: String,
        @SerializedName("total_assignment")
        val totalAssignment: String,
        @SerializedName("total_exam")
        val totalExam: String
    )
}