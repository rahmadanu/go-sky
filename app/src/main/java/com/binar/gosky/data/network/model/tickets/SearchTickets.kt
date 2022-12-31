package com.binar.gosky.data.network.model.tickets

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchTickets(
    var category: String = "",
    var from: String = "",
    var to: String = "",
    var departureTime: String = "",
    var returnTime: String? = null,
    var roundTrip: Boolean = false
) : Parcelable