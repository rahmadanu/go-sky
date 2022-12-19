package com.binar.gosky.data.network.model.users.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserByIdData(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("role")
    val role: String? = null
): Parcelable