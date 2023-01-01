package com.binar.gosky.data.network.model.transactions.earnings


import com.google.gson.annotations.SerializedName

data class EarningsResponse(
    @SerializedName("data")
    val `data`: EarningsData? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)