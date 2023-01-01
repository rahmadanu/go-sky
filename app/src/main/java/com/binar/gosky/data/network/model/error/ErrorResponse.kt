package com.binar.gosky.data.network.model.error


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)