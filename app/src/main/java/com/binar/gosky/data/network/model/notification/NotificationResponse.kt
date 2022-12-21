package com.binar.gosky.data.network.model.notification


import com.binar.gosky.data.network.model.Meta
import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("meta")
    val meta: Meta? = null,
    @SerializedName("data")
    val `data`: List<NotificationData?>? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)