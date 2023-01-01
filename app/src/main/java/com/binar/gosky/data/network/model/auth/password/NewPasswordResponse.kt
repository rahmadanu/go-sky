package com.binar.gosky.data.network.model.auth.password


import com.google.gson.annotations.SerializedName

data class NewPasswordResponse(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)