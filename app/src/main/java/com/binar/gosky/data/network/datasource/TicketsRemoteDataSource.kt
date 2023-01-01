package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.tickets.EditTicketRequestBody
import com.binar.gosky.data.network.model.tickets.EditTicketResponse
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.network.model.tickets.WishlistResponse
import com.binar.gosky.data.network.service.TicketsApiService
import javax.inject.Inject

interface TicketsRemoteDataSource {
    suspend fun getTickets(category: String, from: String, to: String, departureTime: String, returnTime: String?): Tickets
    suspend fun getTicketById(accessToken: String, id: Int): Tickets
    suspend fun getWishlist(accessToken: String): Tickets
    suspend fun postTicketToWishlist(accessToken: String, id: Int): WishlistResponse
    suspend fun deleteTicketFromWishlist(accessToken: String, id: Int): WishlistResponse
    suspend fun postTicket( accessToken: String, postTicketRequest: EditTicketRequestBody): EditTicketResponse
    suspend fun putTicketById(accessToken: String, id: Int, putTicketByIdRequest: EditTicketRequestBody): EditTicketResponse
    suspend fun deleteTicketById(accessToken: String, id: Int): EditTicketResponse
}

class TicketsRemoteDataSourceImpl @Inject constructor(private val apiService: TicketsApiService) :
    TicketsRemoteDataSource {
    override suspend fun getTickets(
        category: String,
        from: String,
        to: String,
        departureTime: String,
        returnTime: String?
    ): Tickets {
        return apiService.getTickets(category, from, to, departureTime, returnTime)
    }

    override suspend fun getTicketById(accessToken: String, id: Int): Tickets {
        return apiService.getTicketById(accessToken, id)
    }

    override suspend fun getWishlist(accessToken: String): Tickets {
        return apiService.getWishlist(accessToken)
    }

    override suspend fun postTicketToWishlist(accessToken: String, id: Int): WishlistResponse {
        return apiService.postTicketToWishlist(accessToken, id)
    }

    override suspend fun deleteTicketFromWishlist(accessToken: String, id: Int): WishlistResponse {
        return apiService.deleteTicketFromWishlist(accessToken, id)
    }

    override suspend fun postTicket(accessToken: String, postTicketRequest: EditTicketRequestBody): EditTicketResponse {
        return apiService.postTicket(accessToken, postTicketRequest)
    }

    override suspend fun putTicketById(
        accessToken: String,
        id: Int,
        putTicketByIdRequest: EditTicketRequestBody
    ): EditTicketResponse {
        return apiService.putTicketById(accessToken, id, putTicketByIdRequest)
    }

    override suspend fun deleteTicketById(accessToken: String, id: Int): EditTicketResponse {
        return apiService.deleteTicketById(accessToken, id)
    }

}