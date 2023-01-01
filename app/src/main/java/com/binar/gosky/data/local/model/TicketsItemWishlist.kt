package com.binar.gosky.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "wishlist_ticket_item")
@Parcelize
data class TicketsItemWishlist(

    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,

    @ColumnInfo(name = "category")
    val category: String? = null,

    @ColumnInfo(name = "departureTime")
    val departureTime: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "flightNumber")
    val flightNumber: String? = null,

    @ColumnInfo(name = "from")
    val from: String? = null,

    @ColumnInfo(name = "imageId")
    val imageId: String? = null,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String? = null,

    @ColumnInfo(name = "price")
    var price: Int? = null,

    @ColumnInfo(name = "returnTime")
    val returnTime: String? = null,

    @ColumnInfo(name = "to")
    val to: String? = null,

    @ColumnInfo(name = "wishlisted")
    val wishlisted: Boolean? = null,

    @ColumnInfo(name = "duration")
    val duration: Long? = null
): Parcelable
