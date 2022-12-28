package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.tickets.Tickets
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET(ApiEndPoints.GET_TICKETS_BY_ID_ENDPOINT)
    suspend fun getTicketById(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    ): Tickets

    @GET(ApiEndPoints.GET_WISHLIST)
    suspend fun getWishlist(
        @Header("Authorization") accessToken: String
    ): Tickets

    @POST(ApiEndPoints.POST_TICKET_TO_WISHLIST)
    suspend fun postTicketToWishlist(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    )

    @DELETE(ApiEndPoints.DELETE_TICKET_FROM_WISHLIST)
    suspend fun deleteTicketFromWishlist(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    )

    companion object {
        private const val DEFAULT_CATEGORY = "ONE_WAY"
        private const val DEPARTURE_TIME = "timestamp"
        private const val RETURN_TIME = "timestamp"
    }
}