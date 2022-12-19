package com.binar.gosky.data.network.model.notification


import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("data")
    val `data`: List<NotificationData?>? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)