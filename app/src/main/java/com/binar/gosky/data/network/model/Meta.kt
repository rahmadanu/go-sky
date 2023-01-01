package com.binar.gosky.data.network.model


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("unreadCount")
    val unreadCount: Int? = null
)