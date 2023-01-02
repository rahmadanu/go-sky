package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.tickets.EditTicketRequestBody
import com.binar.gosky.data.network.model.tickets.EditTicketResponse
import com.binar.gosky.data.network.model.tickets.Tickets
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TicketsApiService {

    @GET(ApiEndPoints.GET_TICKETS_ENDPOINT)
    suspend fun getTickets(
        @Query("category") category: String = DEFAULT_CATEGORY,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("departureTime") departureTime: String = DEPARTURE_TIME,
        @Query("returnTime") returnTime: String? = RETURN_TIME,
    ): Tickets

    @GET(ApiEndPoints.GET_TICKETS_BY_ID_ENDPOINT)
    suspend fun getTicketById(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    ): Tickets

    @POST(ApiEndPoints.POST_TICKET)
    suspend fun postTicket(
        @Header("Authorization") accessToken: String,
        @Body postTicketRequest: EditTicketRequestBody
    ): EditTicketResponse

    @PUT(ApiEndPoints.PUT_TICKETS_BY_ID)
    suspend fun putTicketById(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
        @Body putTicketByIdRequest: EditTicketRequestBody
    ): EditTicketResponse

    @DELETE(ApiEndPoints.DELETE_TICKETS_BY_ID)
    suspend fun deleteTicketById(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    ): EditTicketResponse

    companion object {
        private const val DEFAULT_CATEGORY = "ONE_WAY"
        private const val DEPARTURE_TIME = "timestamp"
        private const val RETURN_TIME = "timestamp"
    }
}