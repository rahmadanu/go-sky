package com.binar.gosky.data.network.model.tickets

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchTickets (
    var from: String,
    var to: String,
    var departureTime: String,
    var returnDate: String,
    var roundTrip: Boolean
): Parcelable