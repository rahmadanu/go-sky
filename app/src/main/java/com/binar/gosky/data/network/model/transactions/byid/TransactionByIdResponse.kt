package com.binar.gosky.data.network.model.transactions.byid


import com.google.gson.annotations.SerializedName

data class TransactionByIdResponse(
    @SerializedName("data")
    val `data`: TransactionByIdData? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)