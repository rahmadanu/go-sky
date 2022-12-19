package com.binar.gosky.data.network.model.auth.register


import com.google.gson.annotations.SerializedName

data class RegisterRequestBody(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("otp")
    val otp: String? = null,
    @SerializedName("otpToken")
    val otpToken: String? = null,
    @SerializedName("password")
    val password: String? = null
)