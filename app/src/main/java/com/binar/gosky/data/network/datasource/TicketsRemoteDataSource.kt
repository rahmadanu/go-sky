package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.network.service.TicketsApiService
import javax.inject.Inject

interface TicketsRemoteDataSource {
    suspend fun getTickets(category: String, from: String, to: String, departureTime: String, returnTime: String): Tickets
    suspend fun getTicketById(accessToken: String, id: Int): Tickets
    suspend fun getWishlist(accessToken: String)
    suspend fun postTicketToWishlist(accessToken: String, id: Int)
    suspend fun deleteTicketFromWishlist(accessToken: String, id: Int)
}

class TicketsRemoteDataSourceImpl @Inject constructor(private val apiService: TicketsApiService) :
    TicketsRemoteDataSource {
    override suspend fun getTickets(
        category: String,
        from: String,
        to: String,
        departureTime: String,
        returnTime: String
    ): Tickets {
        return apiService.getTickets(category, from, to, departureTime, returnTime)
    }

    override suspend fun getTicketById(accessToken: String, id: Int): Tickets {
        return apiService.getTicketById(accessToken, id)
    }

    override suspend fun getWishlist(accessToken: String) {
        apiService.getWishlist(accessToken)
    }

    override suspend fun postTicketToWishlist(accessToken: String, id: Int) {
        apiService.postTicketToWishlist(accessToken, id)
    }

    override suspend fun deleteTicketFromWishlist(accessToken: String, id: Int) {
        apiService.deleteTicketFromWishlist(accessToken, id)
    }

}