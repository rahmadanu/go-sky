package com.binar.gosky.data.network.model.transactions.earnings


import com.google.gson.annotations.SerializedName

data class ThisMonth(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("earnings")
    val earnings: Int? = null
)