package com.binar.gosky.data.network.model.auth.password


import com.google.gson.annotations.SerializedName

data class NewPasswordRequestBody(
    @SerializedName("newPassword")
    val newPassword: String? = null,
    @SerializedName("otp")
    val otp: String? = null,
    @SerializedName("otpToken")
    val otpToken: String? = null
)