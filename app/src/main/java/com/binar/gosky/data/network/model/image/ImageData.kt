package com.binar.gosky.data.network.model.image


import com.google.gson.annotations.SerializedName

data class ImageData(
    @SerializedName("imageId")
    val imageId: String? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null
)