package com.binar.gosky.data.network.model.transactions.byid


import com.google.gson.annotations.SerializedName

data class TransactionByIdData(
    @SerializedName("amount")
    val amount: Int? = null,
    @SerializedName("bookingCode")
    val bookingCode: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("deletedAt")
    val deletedAt: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("ticket")
    val ticket: Ticket? = null,
    @SerializedName("ticketId")
    val ticketId: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("user")
    val user: User? = null,
    @SerializedName("userId")
    val userId: Int? = null
)