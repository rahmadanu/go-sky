package com.binar.gosky.data.network.model.transactions.byid


import com.google.gson.annotations.SerializedName

data class Ticket(
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("createdBy")
    val createdBy: Int? = null,
    @SerializedName("deletedAt")
    val deletedAt: String? = null,
    @SerializedName("departureTime")
    val departureTime: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("duration")
    val duration: Int? = null,
    @SerializedName("flightNumber")
    val flightNumber: String? = null,
    @SerializedName("from")
    val from: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("imageId")
    val imageId: String? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("returnTime")
    val returnTime: String? = null,
    @SerializedName("to")
    val to: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("updatedBy")
    val updatedBy: Int? = null
)