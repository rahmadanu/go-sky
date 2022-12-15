package com.binar.gosky.data.network.model.transactions.new_transaction


import com.google.gson.annotations.SerializedName

data class NewTransactionRequestBody(
    @SerializedName("amount")
    val amount: Int? = null,
    @SerializedName("ticketId")
    val ticketId: Int? = null
)