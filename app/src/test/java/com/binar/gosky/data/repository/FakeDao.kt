package com.binar.gosky.data.repository

import androidx.lifecycle.LiveData
import com.binar.gosky.data.local.database.tickets.TicketsDao
import com.binar.gosky.data.local.model.TicketsItemWishlist

class FakeDao: TicketsDao {
    override suspend fun addTicketToWishlist(ticketsItemWishlist: TicketsItemWishlist): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTicketFromWishlist(ticketsItemWishlist: TicketsItemWishlist): Int {
        TODO("Not yet implemented")
    }

    override fun getWishlistTickets(): LiveData<List<TicketsItemWishlist>> {
        TODO("Not yet implemented")
    }

    override fun isTicketWishlisted(id: Int): LiveData<Boolean> {
        TODO("Not yet implemented")
    }
}