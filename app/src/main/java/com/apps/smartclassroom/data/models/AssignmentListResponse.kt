package com.apps.smartclassroom.data.models


import com.google.gson.annotations.SerializedName

data class AssignmentListResponse(
    @SerializedName("Assignment_details")
    val assignmentDetails: List<AssignmentDetail>,
    @SerializedName("status")
    val status: String
) {
    data class AssignmentDetail(
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
}