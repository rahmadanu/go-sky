package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.tickets.Tickets
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketsApiService {

    @GET(ApiEndPoints.GET_TICKETS_ENDPOINT)
    suspend fun getTickets(
        @Query("category") category: String = CATEGORY,
        @Query("from") from: String = FROM,
        @Query("to") to: String = TO,
        @Query("departureTime") departureTime: String = DEPARTURE_TIME,
        @Query("returnTime") returnTime: String = RETURN_TIME,
    ): Tickets

    companion object {
        private const val CATEGORY = "ONE_WAY"
        private const val FROM = "JAKARTA"
        private const val TO = "MEDAN"
        private const val DEPARTURE_TIME = "timestamp"
        private const val RETURN_TIME = "timestamp"
    }
}