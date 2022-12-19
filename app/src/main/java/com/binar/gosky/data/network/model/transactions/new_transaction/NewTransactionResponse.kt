package com.binar.gosky.data.network.model.transactions.new_transaction


import com.google.gson.annotations.SerializedName

data class NewTransactionResponse(
    @SerializedName("data")
    val `data`: NewTransactionData? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)