package com.binar.gosky.data.repository

import androidx.lifecycle.LiveData
import com.binar.gosky.data.local.datasource.TicketsLocalDataSource
import com.binar.gosky.data.local.model.TicketsItemWishlist
import com.binar.gosky.data.network.datasource.TicketsRemoteDataSource
import com.binar.gosky.data.network.model.tickets.EditTicketRequestBody
import com.binar.gosky.data.network.model.tickets.EditTicketResponse
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.network.model.tickets.TicketsItem
import com.binar.gosky.util.proceed
import com.binar.gosky.data.local.mapper.toTicketsItemWishlist
import com.binar.gosky.data.network.model.tickets.WishlistResponse
import com.binar.gosky.wrapper.Resource
import javax.inject.Inject

interface TicketsRepository {
    suspend fun getTickets(category: String, from: String, to: String, departureTime: String, returnTime: String?): Resource<Tickets>
    suspend fun getTicketById(accessToken: String, id: Int): Resource<Tickets>
    suspend fun getWishlist(accessToken: String): Resource<Tickets>
    suspend fun postTicketToRemoteWishlist(accessToken: String, id: Int): Resource<WishlistResponse>
    suspend fun deleteTicketFromRemoteWishlist(accessToken: String, id: Int): Resource<WishlistResponse>

    suspend fun addTicketToLocalWishlist(ticketsItem: TicketsItem): Resource<Number>
    suspend fun deleteTicketFromLocalWishlist(ticketsItem: TicketsItem): Resource<Number>
    fun getWishlistTickets(): LiveData<List<TicketsItemWishlist>>
    fun isTicketWishlisted(id: Int): LiveData<Boolean>

    suspend fun postTicket( accessToken: String, postTicketRequest: EditTicketRequestBody): Resource<EditTicketResponse>
    suspend fun putTicketById(accessToken: String, id: Int, putTicketByIdRequest: EditTicketRequestBody): Resource<EditTicketResponse>
    suspend fun deleteTicketById(accessToken: String, id: Int): Resource<EditTicketResponse>
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
        returnTime: String?
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

    override suspend fun postTicketToRemoteWishlist(accessToken: String, id: Int): Resource<WishlistResponse> {
        return proceed {
            remoteDataSource.postTicketToWishlist(accessToken, id)
        }
    }

    override suspend fun deleteTicketFromRemoteWishlist(accessToken: String, id: Int): Resource<WishlistResponse> {
        return proceed {
            remoteDataSource.deleteTicketFromWishlist(accessToken, id)
        }
    }

    override suspend fun deleteTicketFromLocalWishlist(ticketsItem: TicketsItem): Resource<Number> {
        return proceed {
            localDataSource.deleteTicketFromWishlist(ticketsItem.toTicketsItemWishlist())
        }
    }

    override suspend fun addTicketToLocalWishlist(ticketsItem: TicketsItem): Resource<Number> {
        return proceed {
            localDataSource.addTicketToWishlist(ticketsItem.toTicketsItemWishlist())
        }
    }

    override fun getWishlistTickets(): LiveData<List<TicketsItemWishlist>> {
        return localDataSource.getWishlistTickets()
    }

    override fun isTicketWishlisted(id: Int): LiveData<Boolean> {
        return localDataSource.isTicketWishlisted(id)
    }

    override suspend fun postTicket(accessToken: String, postTicketRequest: EditTicketRequestBody): Resource<EditTicketResponse> {
        return proceed {
            remoteDataSource.postTicket(accessToken, postTicketRequest)
        }
    }

    override suspend fun putTicketById(
        accessToken: String,
        id: Int,
        putTicketByIdRequest: EditTicketRequestBody
    ): Resource<EditTicketResponse> {
        return proceed {
            remoteDataSource.putTicketById(accessToken, id, putTicketByIdRequest)
        }
    }

    override suspend fun deleteTicketById(accessToken: String, id: Int): Resource<EditTicketResponse> {
        return proceed {
            remoteDataSource.deleteTicketById(accessToken, id)
        }
    }

}