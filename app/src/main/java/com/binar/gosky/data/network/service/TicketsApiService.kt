package com.binar.gosky.data.network.service

import android.provider.MediaStore.Video.VideoColumns.CATEGORY
import com.binar.gosky.data.network.model.auth.otp.OtpResponse
import com.binar.gosky.data.network.model.tickets.Tickets
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketsApiService {

    @GET(ApiEndPoints.GET_TICKETS_ENDPOINT)
    suspend fun getTickets(
        @Query("category") category: String = DEFAULT_CATEGORY,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("departureTime") departureTime: String = DEPARTURE_TIME,
        @Query("returnTime") returnTime: String = RETURN_TIME,
    ): Tickets

    companion object {
        private const val DEFAULT_CATEGORY = "ONE_WAY"
        private const val DEPARTURE_TIME = "timestamp"
        private const val RETURN_TIME = "timestamp"
    }
}