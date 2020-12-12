package com.app.smartclassroom.data.models


import com.google.gson.annotations.SerializedName

data class SubjectListResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("Subject")
    val subject: List<Subject>
) {
    data class Subject(
        @SerializedName("sub_id")
        val subId: String,
        @SerializedName("subject")
        val subject: String
    )
}