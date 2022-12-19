package com.binar.gosky.data.network.model.users.data


import com.google.gson.annotations.SerializedName

data class UserByIdResponse(
    @SerializedName("data")
    val `data`: UserByIdData? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)