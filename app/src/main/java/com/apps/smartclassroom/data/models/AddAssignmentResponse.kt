package com.apps.smartclassroom.data.models


import com.google.gson.annotations.SerializedName

data class AddAssignmentResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("url")
    val url: String
)