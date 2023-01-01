package com.binar.gosky.data.network.model.users.password


import com.google.gson.annotations.SerializedName

data class NewPasswordResetRequestBody(
    @SerializedName("newPassword")
    val newPassword: String? = null,
    @SerializedName("password")
    val password: String? = null
)