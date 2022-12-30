package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.TicketsRemoteDataSource
import com.binar.gosky.data.network.model.tickets.EditTicketRequestBody
import com.binar.gosky.data.network.model.tickets.EditTicketResponse
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.util.proceed
import com.binar.gosky.wrapper.Resource
import javax.inject.Inject

interface TicketsRepository {
    suspend fun getTickets(category: String, from: String, to: String, departureTime: String, returnTime: String): Resource<Tickets>
    suspend fun getTicketById(accessToken: String, id: Int): Resource<Tickets>
    suspend fun postTicket( accessToken: String, postTicketRequest: EditTicketRequestBody)
    suspend fun putTicketById(accessToken: String, id: Int, putTicketByIdRequest: EditTicketRequestBody): Resource<EditTicketResponse>
    suspend fun deleteTicketById(accessToken: String, id: Int)
}

class TicketsRepositoryImpl @Inject constructor(private val dataSource: TicketsRemoteDataSource) :
    TicketsRepository {

    override suspend fun getTickets(
        category: String,
        from: String,
        to: String,
        departureTime: String,
        returnTime: String
    ): Resource<Tickets> {
        return proceed {
            dataSource.getTickets(category, from, to, departureTime, returnTime)
        }
    }

    override suspend fun getTicketById(accessToken: String, id: Int): Resource<Tickets> {
        return proceed {
            dataSource.getTicketById(accessToken, id)
        }
    }

    override suspend fun postTicket(accessToken: String, postTicketRequest: EditTicketRequestBody) {
        dataSource.postTicket(accessToken, postTicketRequest)
    }

    override suspend fun putTicketById(
        accessToken: String,
        id: Int,
        putTicketByIdRequest: EditTicketRequestBody
    ): Resource<EditTicketResponse> {
        return proceed {
            dataSource.putTicketById(accessToken, id, putTicketByIdRequest)
        }
    }

    override suspend fun deleteTicketById(accessToken: String, id: Int) {
        dataSource.deleteTicketById(accessToken, id)
    }

}