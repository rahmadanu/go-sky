package com.binar.gosky.data.network.model.transactions.new_transaction


import com.google.gson.annotations.SerializedName

data class NewTransactionData(
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
    @SerializedName("ticketId")
    val ticketId: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
)