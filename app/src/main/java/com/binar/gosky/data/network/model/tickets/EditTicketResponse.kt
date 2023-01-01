package com.binar.gosky.data.network.model.tickets


import com.google.gson.annotations.SerializedName

data class EditTicketResponse(
    @SerializedName("data")
    val `data`: TicketsItem? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)