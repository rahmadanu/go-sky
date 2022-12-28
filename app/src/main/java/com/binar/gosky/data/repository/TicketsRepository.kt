package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.TicketsRemoteDataSource
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.network.service.ApiEndPoints
import com.binar.gosky.util.proceed
import com.binar.gosky.wrapper.Resource
import retrofit2.http.*
import javax.inject.Inject

interface TicketsRepository {
    suspend fun getTickets(category: String, from: String, to: String, departureTime: String, returnTime: String): Resource<Tickets>
    suspend fun getTicketById(accessToken: String, id: Int): Resource<Tickets>
    suspend fun getWishlist(accessToken: String): Resource<Tickets>
    suspend fun postTicketToWishlist(accessToken: String, id: Int)
    suspend fun deleteTicketFromWishlist(accessToken: String, id: Int)
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

    override suspend fun getWishlist(accessToken: String): Resource<Tickets> {
        return proceed {
            dataSource.getWishlist(accessToken)
        }
    }

    override suspend fun postTicketToWishlist(accessToken: String, id: Int) {
        dataSource.postTicketToWishlist(accessToken, id)
    }

    override suspend fun deleteTicketFromWishlist(accessToken: String, id: Int) {
        dataSource.deleteTicketFromWishlist(accessToken, id)
    }

}