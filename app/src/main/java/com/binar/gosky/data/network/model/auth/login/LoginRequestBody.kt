package com.binar.gosky.data.network.model.auth.login


import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("password")
    val password: String? = null
)