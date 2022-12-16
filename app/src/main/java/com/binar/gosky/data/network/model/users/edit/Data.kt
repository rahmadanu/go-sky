package com.binar.gosky.data.network.model.users.edit


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("deletedAt")
    val deletedAt: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("encryptedPassword")
    val encryptedPassword: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("imageId")
    val imageId: String? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("role")
    val role: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null
)