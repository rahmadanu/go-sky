package com.binar.gosky.data.network.model.transactions.earnings


import com.google.gson.annotations.SerializedName

data class EarningsData(
    @SerializedName("thisMonth")
    val thisMonth: ThisMonth? = null,
    @SerializedName("thisYear")
    val thisYear: ThisYear? = null,
    @SerializedName("today")
    val today: Today? = null
)