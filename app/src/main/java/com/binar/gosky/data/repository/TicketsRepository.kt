package com.binar.gosky.data.repository

import androidx.lifecycle.LiveData
import com.binar.gosky.data.local.datasource.TicketsLocalDataSource
import com.binar.gosky.data.local.model.TicketsItemWishlist
import com.binar.gosky.data.network.datasource.TicketsRemoteDataSource
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.util.proceed
import com.binar.gosky.wrapper.Resource
import javax.inject.Inject

interface TicketsRepository {
    suspend fun getTickets(category: String, from: String, to: String, departureTime: String, returnTime: String): Resource<Tickets>
    suspend fun getTicketById(accessToken: String, id: Int): Resource<Tickets>
    suspend fun getWishlist(accessToken: String): Resource<Tickets>
    suspend fun postTicketToWishlist(accessToken: String, id: Int)
    suspend fun deleteTicketFromWishlist(accessToken: String, id: Int)

    suspend fun addTicketToWishlist(ticketsItemWishlist: TicketsItemWishlist): Resource<Number>
    suspend fun deleteTicketFromWishlist(ticketsItemWishlist: TicketsItemWishlist): Resource<Number>
    fun getWishlistTickets(): LiveData<List<TicketsItemWishlist>>
    fun isTicketWishlisted(id: Int): LiveData<Boolean>
}

class TicketsRepositoryImpl @Inject constructor(
    private val remoteDataSource: TicketsRemoteDataSource,
    private val localDataSource: TicketsLocalDataSource,
) : TicketsRepository {

    override suspend fun getTickets(
        category: String,
        from: String,
        to: String,
        departureTime: String,
        returnTime: String
    ): Resource<Tickets> {
        return proceed {
            remoteDataSource.getTickets(category, from, to, departureTime, returnTime)
        }
    }

    override suspend fun getTicketById(accessToken: String, id: Int): Resource<Tickets> {
        return proceed {
            remoteDataSource.getTicketById(accessToken, id)
        }
    }

    override suspend fun getWishlist(accessToken: String): Resource<Tickets> {
        return proceed {
            remoteDataSource.getWishlist(accessToken)
        }
    }

    override suspend fun postTicketToWishlist(accessToken: String, id: Int) {
        remoteDataSource.postTicketToWishlist(accessToken, id)
    }

    override suspend fun deleteTicketFromWishlist(accessToken: String, id: Int) {
        remoteDataSource.deleteTicketFromWishlist(accessToken, id)
    }

    override suspend fun deleteTicketFromWishlist(ticketsItemWishlist: TicketsItemWishlist): Resource<Number> {
        return proceed {
            localDataSource.deleteTicketFromWishlist(ticketsItemWishlist)
        }
    }

    override suspend fun addTicketToWishlist(ticketsItemWishlist: TicketsItemWishlist): Resource<Number> {
        return proceed {
            localDataSource.addTicketToWishlist(ticketsItemWishlist)
        }
    }

    override fun getWishlistTickets(): LiveData<List<TicketsItemWishlist>> {
        return localDataSource.getWishlistTickets()
    }

    override fun isTicketWishlisted(id: Int): LiveData<Boolean> {
        return localDataSource.isTicketWishlisted(id)
    }

}