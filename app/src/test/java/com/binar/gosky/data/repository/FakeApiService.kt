package com.binar.gosky.data.repository

import com.binar.gosky.DummyData
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.network.service.TicketsApiService

class FakeApiService: TicketsApiService {
    override suspend fun getTickets(
        category: String,
        from: String,
        to: String,
        departureTime: String,
        returnTime: String
    ): Tickets {
        return DummyData.getDummyTickets()
    }

    override suspend fun getTicketById(accessToken: String, id: Int): Tickets {
        TODO("Not yet implemented")
    }
}