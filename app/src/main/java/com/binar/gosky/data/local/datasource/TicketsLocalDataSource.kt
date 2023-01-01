package com.binar.gosky.data.local.datasource

import androidx.lifecycle.LiveData
import com.binar.gosky.data.local.database.tickets.TicketsDao
import com.binar.gosky.data.local.model.TicketsItemWishlist
import javax.inject.Inject

interface TicketsLocalDataSource {
    suspend fun addTicketToWishlist(ticketsItemWishlist: TicketsItemWishlist): Long
    suspend fun deleteTicketFromWishlist(ticketsItemWishlist: TicketsItemWishlist): Int
    fun getWishlistTickets(): LiveData<List<TicketsItemWishlist>>
    fun isTicketWishlisted(id: Int): LiveData<Boolean>
}

class TicketsLocalDataSourceImpl @Inject constructor(private val ticketsDao: TicketsDao) :
    TicketsLocalDataSource {
    override suspend fun addTicketToWishlist(ticketsItemWishlist: TicketsItemWishlist): Long {
        return ticketsDao.addTicketToWishlist(ticketsItemWishlist)
    }

    override suspend fun deleteTicketFromWishlist(ticketsItemWishlist: TicketsItemWishlist): Int {
        return ticketsDao.deleteTicketFromWishlist(ticketsItemWishlist)
    }

    override fun getWishlistTickets(): LiveData<List<TicketsItemWishlist>> {
        return ticketsDao.getWishlistTickets()
    }

    override fun isTicketWishlisted(id: Int): LiveData<Boolean> {
        return ticketsDao.isTicketWishlisted(id)
    }

}