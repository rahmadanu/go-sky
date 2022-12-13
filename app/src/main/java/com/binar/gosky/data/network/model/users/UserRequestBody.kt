package com.binar.gosky.data.network.model.users


import com.google.gson.annotations.SerializedName

data class UserRequestBody(
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("imageId")
    val imageId: String? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone")
    val phone: String? = null
)