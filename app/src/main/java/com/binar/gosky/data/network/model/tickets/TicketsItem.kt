package com.binar.gosky.data.network.model.tickets


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketsItem(
    @SerializedName("category")
    var category: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("createdBy")
    val createdBy: Int? = null,
    @SerializedName("deletedAt")
    val deletedAt: String? = null,
    @SerializedName("departureTime")
    val departureTime: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("flightNumber")
    val flightNumber: String? = null,
    @SerializedName("from")
    val from: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("imageId")
    var imageId: String? = null,
    @SerializedName("imageUrl")
    var imageUrl: String? = null,
    @SerializedName("price")
    var price: Int? = null,
    @SerializedName("returnTime")
    val returnTime: String? = null,
    @SerializedName("to")
    val to: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("updatedBy")
    val updatedBy: Int? = null,
    @SerializedName("wishlisted")
    val wishlisted: Boolean? = null,
    @SerializedName("duration")
    val duration: Long? = null
): Parcelable