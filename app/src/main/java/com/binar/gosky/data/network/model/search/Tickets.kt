package com.binar.gosky.data.network.model.search


import com.google.gson.annotations.SerializedName

data class Tickets(
    @SerializedName("data")
    val `data`: List<TicketsItem>? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("meta")
    val meta: Meta? = null,
    @SerializedName("status")
    val status: String? = null
)