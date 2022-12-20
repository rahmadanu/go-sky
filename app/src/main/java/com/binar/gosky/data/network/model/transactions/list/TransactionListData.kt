package com.binar.gosky.data.network.model.transactions.list


import com.google.gson.annotations.SerializedName

data class TransactionListData(
    @SerializedName("amount")
    val amount: Int? = null,
    @SerializedName("bookingCode")
    val bookingCode: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("deletedAt")
    val deletedAt: Any? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("ticket")
    val ticket: TransactionTicketItem? = null,
    @SerializedName("ticketId")
    val ticketId: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
)