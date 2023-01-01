package com.binar.gosky.data.network.model.tickets


import com.google.gson.annotations.SerializedName

data class WishlistResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)