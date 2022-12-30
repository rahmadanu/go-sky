package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.tickets.EditTicketRequestBody
import com.binar.gosky.data.network.model.tickets.EditTicketResponse
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.network.service.TicketsApiService
import javax.inject.Inject

interface TicketsRemoteDataSource {
    suspend fun getTickets(category: String, from: String, to: String, departureTime: String, returnTime: String): Tickets
    suspend fun getTicketById(accessToken: String, id: Int): Tickets
    suspend fun postTicket( accessToken: String, postTicketRequest: EditTicketRequestBody)
    suspend fun putTicketById(accessToken: String, id: Int, putTicketByIdRequest: EditTicketRequestBody): EditTicketResponse
    suspend fun deleteTicketById(accessToken: String, id: Int)
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

    override suspend fun postTicket(accessToken: String, postTicketRequest: EditTicketRequestBody) {
        apiService.postTicket(accessToken, postTicketRequest)
    }

    override suspend fun putTicketById(
        accessToken: String,
        id: Int,
        putTicketByIdRequest: EditTicketRequestBody
    ): EditTicketResponse {
        return apiService.putTicketById(accessToken, id, putTicketByIdRequest)
    }

    override suspend fun deleteTicketById(accessToken: String, id: Int) {
        apiService.deleteTicketById(accessToken, id)
    }

}