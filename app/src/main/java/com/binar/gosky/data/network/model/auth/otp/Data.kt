package com.binar.gosky.data.network.model.auth.otp


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("otpToken")
    val otpToken: String? = null
)