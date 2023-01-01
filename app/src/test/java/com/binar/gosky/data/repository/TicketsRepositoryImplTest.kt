package com.binar.gosky.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.gosky.DummyData
import com.binar.gosky.data.local.database.tickets.TicketsDao
import com.binar.gosky.data.local.datasource.TicketsLocalDataSource
import com.binar.gosky.data.local.datasource.TicketsLocalDataSourceImpl
import com.binar.gosky.rule.MainDispatcherRule
import com.binar.gosky.data.network.datasource.TicketsRemoteDataSource
import com.binar.gosky.data.network.model.tickets.TicketsItem
import com.binar.gosky.data.network.service.TicketsApiService
import com.binar.gosky.wrapper.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TicketsRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var apiService: TicketsApiService
    private lateinit var ticketsDao: TicketsDao
    private lateinit var remoteDataSource: TicketsRemoteDataSource
    private lateinit var localDataSource: TicketsLocalDataSource
    private lateinit var repository: TicketsRepository

    @Before
    fun setUp() {
        apiService = FakeApiService()
        ticketsDao = FakeDao()
        remoteDataSource = FakeTicketsRemoteDataSource(apiService)
        localDataSource = TicketsLocalDataSourceImpl(ticketsDao)
        repository = TicketsRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `when getTickets should not be null`() = runTest {
        val expectedResult = DummyData.getDummyTickets()
        val expectedResultInNumber = 10
        val actualResult = repository.getTickets(
            category = "ONE_WAY",
            from = "JAKARTA",
            to = "MEDAN",
            departureTime = "timestamp",
            returnTime = "timestamp"
        )
        assertNotNull(actualResult)
        assertEquals(
            //expectedResult.data?.size
            expectedResultInNumber,
            (actualResult as Resource.Success).data?.data?.size)
    }

/*
    @Test
    fun `when search query is empty`() = runTest {
        //given
        val query = ""

        //when
        repository.getTickets(
            category = "",
            from = "",
            to = "",
            departureTime = "",
            returnTime = ""
        )

        //then
        val actual = repository.getTickets(
            category = "",
            from = "",
            to = "",
            departureTime = "",
            returnTime = ""
        )
        Assert.assertTrue(actual.payload?.data.isNullOrEmpty())
    }
*/

    @Test
    fun `when tickets item should exist in tickets list`() = runTest {
        val sampleTicketsList: List<TicketsItem> = DummyData.getDummyTickets().data?.map {
            TicketsItem(from = it.from, to = it.to)
        }!!
        val sample = TicketsItem(
            from = "JAKARTA",
            to = "MEDAN"
        )
        val sample2 = TicketsItem(
            from = "BANDUNG",
            to = "MEDAN"
        )
        val actualResult = repository.getTickets(
            category = "ONE_WAY",
            from = "JAKARTA",
            to = "MEDAN",
            departureTime = "timestamp",
            returnTime = "timestamp"
        ) as Resource.Success
        actualResult.data?.data?.contains(sampleTicketsList[0])?.let { assertTrue(it) }
        actualResult.data?.data?.contains(sample)?.let { assertTrue(it) }
        actualResult.data?.data?.contains(sample2)?.let { assertFalse(it) }
    }

}
