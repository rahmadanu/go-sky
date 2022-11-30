package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.TicketsRemoteDataSource
import com.binar.gosky.data.network.model.search.Tickets
import javax.inject.Inject

interface TicketsRepository {
    suspend fun getTickets(): Tickets
}

class TicketsRepositoryImpl @Inject constructor(private val dataSource: TicketsRemoteDataSource) :
    TicketsRepository {
    override suspend fun getTickets(): Tickets {
        return dataSource.getTickets()
    }

}