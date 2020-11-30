package com.harish.smartclassroom.data.models


import com.google.gson.annotations.SerializedName

data class BatchResponse(
    @SerializedName("batch_details")
    val batchDetails: List<BatchDetail>,
    @SerializedName("status")
    val status: String
) {
    data class BatchDetail(
        @SerializedName("batch_id")
        val batchId: String,
        @SerializedName("batch_name")
        val batchName: String
    )
}