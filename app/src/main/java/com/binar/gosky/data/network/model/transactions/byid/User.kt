package com.binar.gosky.data.network.model.transactions.byid


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("role")
    val role: String? = null
)