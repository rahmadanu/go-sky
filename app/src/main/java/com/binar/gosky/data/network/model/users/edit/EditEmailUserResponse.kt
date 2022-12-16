package com.binar.gosky.data.network.model.users.edit


import com.google.gson.annotations.SerializedName

data class EditEmailUserResponse(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)