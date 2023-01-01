package com.binar.gosky.data.network.model.auth.password


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("accessToken")
    val accessToken: String? = null
)