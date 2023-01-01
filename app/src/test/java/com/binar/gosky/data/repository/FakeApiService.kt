package com.binar.gosky.data.repository

import com.binar.gosky.DummyData
import com.binar.gosky.data.network.model.tickets.EditTicketRequestBody
import com.binar.gosky.data.network.model.tickets.EditTicketResponse
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.network.model.tickets.WishlistResponse
import com.binar.gosky.data.network.service.TicketsApiService

class FakeApiService: TicketsApiService {

    override suspend fun getTickets(
        category: String,
        from: String,
        to: String,
        departureTime: String,
        returnTime: String?
    ): Tickets {
        return DummyData.getDummyTickets()
    }

    override suspend fun getTicketById(accessToken: String, id: Int): Tickets {
        TODO("Not yet implemented")
    }

    override suspend fun getWishlist(accessToken: String): Tickets {
        TODO("Not yet implemented")
    }

    override suspend fun postTicketToWishlist(accessToken: String, id: Int): WishlistResponse {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTicketFromWishlist(accessToken: String, id: Int): WishlistResponse {
        TODO("Not yet implemented")
    }

    override suspend fun postTicket(
        accessToken: String,
        postTicketRequest: EditTicketRequestBody
    ): EditTicketResponse {
        TODO("Not yet implemented")
    }

    override suspend fun putTicketById(
        accessToken: String,
        id: Int,
        putTicketByIdRequest: EditTicketRequestBody
    ): EditTicketResponse {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTicketById(accessToken: String, id: Int): EditTicketResponse {
        TODO("Not yet implemented")
    }
}