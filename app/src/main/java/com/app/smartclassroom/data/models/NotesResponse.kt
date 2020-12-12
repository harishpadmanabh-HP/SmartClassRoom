package com.app.smartclassroom.data.models


import com.google.gson.annotations.SerializedName

data class NotesResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("study_material")
    val studyMaterial: List<StudyMaterial>?
) {
    data class StudyMaterial(
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
}