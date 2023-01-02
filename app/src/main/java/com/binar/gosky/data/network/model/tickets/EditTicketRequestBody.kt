package com.binar.gosky.data.network.model.tickets


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditTicketRequestBody(
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("departureTime")
    val departureTime: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("flightNumber")
    val flightNumber: String? = null,
    @SerializedName("from")
    val from: String? = null,
    @SerializedName("imageId")
    val imageId: String? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("returnTime")
    val returnTime: String? = null,
    @SerializedName("to")
    val to: String? = null,
    @SerializedName("duration")
    val duration: Long? = null,
    @SerializedName("wishlisted")
    val wishlisted: Boolean? = null,
    ): Parcelable