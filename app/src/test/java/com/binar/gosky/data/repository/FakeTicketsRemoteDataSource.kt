package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.TicketsRemoteDataSource
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.network.service.TicketsApiService

//class FakeTicketsRemoteDataSource(private val apiService: TicketsApiService): TicketsRemoteDataSource {
//    override suspend fun getTickets(
//        category: String,
//        from: String,
//        to: String,
//        departureTime: String,
//        returnTime: String
//    ): Tickets {
//        return apiService.getTickets(category, from, to, departureTime, returnTime)
//    }
//}
