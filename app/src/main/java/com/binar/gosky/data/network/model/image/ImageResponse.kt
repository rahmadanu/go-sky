package com.binar.gosky.data.network.model.image


import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("data")
    val `data`: ImageData? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)