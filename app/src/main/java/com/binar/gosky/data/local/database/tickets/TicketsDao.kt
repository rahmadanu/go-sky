package com.binar.gosky.data.local.database.tickets

import androidx.lifecycle.LiveData
import androidx.room.*
import com.binar.gosky.data.local.model.TicketsItemWishlist

@Dao
interface TicketsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTicketToWishlist(ticketsItemWishlist: TicketsItemWishlist): Long

    @Delete
    suspend fun deleteTicketFromWishlist(ticketsItemWishlist: TicketsItemWishlist): Int

    @Query("SELECT * FROM wishlist_ticket_item")
    fun getWishlistTickets(): LiveData<List<TicketsItemWishlist>>

    @Query("SELECT EXISTS(SELECT * FROM wishlist_ticket_item WHERE id = :id)")
    fun isTicketWishlisted(id: Int): LiveData<Boolean>
}