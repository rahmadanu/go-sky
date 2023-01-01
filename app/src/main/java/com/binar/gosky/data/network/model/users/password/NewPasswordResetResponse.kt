package com.binar.gosky.data.network.model.users.password


import com.google.gson.annotations.SerializedName

data class NewPasswordResetResponse(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)