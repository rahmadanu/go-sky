package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.TicketsRemoteDataSource
import com.binar.gosky.data.network.model.tickets.AddUpdateRequestBody
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.util.proceed
import com.binar.gosky.wrapper.Resource
import javax.inject.Inject

interface TicketsRepository {
    suspend fun getTickets(category: String, from: String, to: String, departureTime: String, returnTime: String): Resource<Tickets>
    suspend fun getTicketById(accessToken: String, id: Int): Resource<Tickets>
    suspend fun postTicket( accessToken: String, postTicketRequest: AddUpdateRequestBody)
    suspend fun putTicketById(accessToken: String, id: Int, putTicketByIdRequest: AddUpdateRequestBody)
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

    override suspend fun postTicket(accessToken: String, postTicketRequest: AddUpdateRequestBody) {
        dataSource.postTicket(accessToken, postTicketRequest)
    }

    override suspend fun putTicketById(
        accessToken: String,
        id: Int,
        putTicketByIdRequest: AddUpdateRequestBody
    ) {
        dataSource.putTicketById(accessToken, id, putTicketByIdRequest)
    }

    override suspend fun deleteTicketById(accessToken: String, id: Int) {
        dataSource.deleteTicketById(accessToken, id)
    }

}