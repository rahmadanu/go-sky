package com.binar.gosky.data.network.model.auth.otp


import com.google.gson.annotations.SerializedName

data class OtpResponse(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)