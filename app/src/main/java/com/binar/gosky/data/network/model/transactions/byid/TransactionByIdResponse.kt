package com.binar.gosky.data.network.model.transactions.byid


import com.google.gson.annotations.SerializedName

data class TransactionByIdResponse(
    @SerializedName("data")
    val `data`: TransactionByIdData? = TransactionByIdData(),
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("status")
    val status: String? = ""
)