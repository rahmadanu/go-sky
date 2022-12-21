package com.binar.gosky.data.network.model.transactions.list


import com.binar.gosky.data.network.model.Meta
import com.google.gson.annotations.SerializedName

data class TransactionListResponse(
    @SerializedName("data")
    val `data`: List<TransactionListData>? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("meta")
    val meta: Meta? = null,
    @SerializedName("status")
    val status: String? = null
)