package com.binar.gosky.data.network.model.auth.user


import com.google.gson.annotations.SerializedName

data class CurrentUserResponse(
    @SerializedName("data")
    val `data`: CurrentUserData? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)