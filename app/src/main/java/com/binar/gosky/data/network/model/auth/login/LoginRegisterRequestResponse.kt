package com.binar.gosky.data.network.model.auth.login


import com.google.gson.annotations.SerializedName

data class LoginRegisterRequestResponse(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)