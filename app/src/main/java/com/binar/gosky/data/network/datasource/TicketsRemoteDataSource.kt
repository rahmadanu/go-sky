package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.network.service.TicketsApiService
import javax.inject.Inject

interface TicketsRemoteDataSource {
    suspend fun getTickets(): Tickets
}

class TicketsRemoteDataSourceImpl @Inject constructor(private val apiService: TicketsApiService): TicketsRemoteDataSource {
    override suspend fun getTickets(): Tickets {
        return apiService.getTickets()
    }

}