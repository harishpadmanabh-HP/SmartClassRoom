package com.apps.smartclassroom.data.models


import com.google.gson.annotations.SerializedName

data class AddAssignmentResponse(
    @SerializedName("status")
    val status: Boolean
)