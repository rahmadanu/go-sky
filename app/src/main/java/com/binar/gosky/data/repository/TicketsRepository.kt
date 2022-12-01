package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.TicketsRemoteDataSource
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.wrapper.Resource
import javax.inject.Inject

interface TicketsRepository {
    suspend fun getTickets(): Resource<Tickets>
}

class TicketsRepositoryImpl @Inject constructor(private val dataSource: TicketsRemoteDataSource) :
    TicketsRepository {
    override suspend fun getTickets(): Resource<Tickets> {
        return proceed {
            dataSource.getTickets()
        }
    }

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}