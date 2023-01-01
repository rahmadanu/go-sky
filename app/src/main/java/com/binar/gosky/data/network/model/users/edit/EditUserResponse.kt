package com.binar.gosky.data.network.model.users.edit


import com.binar.gosky.data.network.model.users.data.Data
import com.google.gson.annotations.SerializedName

data class EditUserResponse(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)