package com.binar.gosky.data.network.model.users.edit


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditUserRequestBody(
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("imageId")
    var imageId: String? = null,
    @SerializedName("imageUrl")
    var imageUrl: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone")
    val phone: String? = null
): Parcelable