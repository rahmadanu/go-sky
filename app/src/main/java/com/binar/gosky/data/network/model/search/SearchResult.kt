package com.binar.gosky.data.network.model.search


import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("data")
    val `data`: List<SearchResultItem?>? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("meta")
    val meta: Meta? = null,
    @SerializedName("status")
    val status: String? = null
)