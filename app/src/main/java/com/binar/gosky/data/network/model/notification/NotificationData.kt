package com.binar.gosky.data.network.model.notification


import com.google.gson.annotations.SerializedName

data class NotificationData(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("deletedAt")
    val deletedAt: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("isRead")
    val isRead: Any? = null,
    @SerializedName("messaage")
    val messaage: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
)