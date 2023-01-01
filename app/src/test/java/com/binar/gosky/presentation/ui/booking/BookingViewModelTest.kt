package com.binar.gosky.presentation.ui.booking

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.gosky.data.network.datasource.TicketsRemoteDataSource
import com.binar.gosky.data.network.datasource.TicketsRemoteDataSourceImpl
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionRequestBody
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionResponse
import com.binar.gosky.data.network.service.TicketsApiService
import com.binar.gosky.data.repository.TransactionsRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.getOrAwaitValue
import com.binar.gosky.wrapper.Resource
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


@ExperimentalCoroutinesApi
class BookingViewModelTest {

    private lateinit var viewModel: BookingViewModel
    private lateinit var transactionRepository: TransactionsRepository
    private lateinit var userRepository: UserRepository
    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var apiService: TicketsApiService
    private lateinit var remoteDataSource: TicketsRemoteDataSource

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        transactionRepository = mockk()
        userRepository = mockk()
        viewModel = BookingViewModel(transactionRepository, userRepository)
        apiService = mockk()
        remoteDataSource = TicketsRemoteDataSourceImpl(apiService)
    }

    @Test
    fun postTicket() {
        val postOrderTicketResponse = mockk<Resource<NewTransactionResponse>>()

        val accessToken =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NCwibmFtZSI6ImFkbWluIiwiZW1haWwiOiJnb3NreS5hZG1pbkBnbWFpbC5jb20iLCJlbmNyeXB0ZWRQYXNzd29yZCI6IiQyYSQxMCROQ0wzYVZkd00wY09ZTVltQk1yRnMuRnRXQlJuaUxpUmU5UlpWLkhTU1BYRjMwcDhCTEFRbSIsInJvbGUiOiJBRE1JTiIsInBob25lIjoiMDgxMjM0MzEyMzQzIiwiYWRkcmVzcyI6Impha2FydGEiLCJpbWFnZUlkIjoiNDExMzExMzEiLCJpbWFnZVVybCI6Imh0dHBzOi8vd3d3LnNodXR0ZXJzdG9jay5jb20vaW1hZ2UtdmVjdG9yL3RocmVlLXBlcnNvbnMtYWRtaW4taWNvbi1vdXRsaW5lLTYwMHctMTczMDk3NDE2NS5qcGciLCJkZWxldGVkQXQiOm51bGwsImNyZWF0ZWRBdCI6IjIwMjItMTItMzBUMDY6MzI6MjcuNzM1WiIsInVwZGF0ZWRBdCI6IjIwMjItMTItMzBUMDY6MzI6MjcuNzM1WiIsImlhdCI6MTY3MjM4MzE4OH0.HmfoJSE0QUc5DEFlFAXCj50xREMfB2rWJinW6NWBUM0"

        val newTransactionRequestBody = NewTransactionRequestBody(2, 5)

//        every {
//            runBlocking {
//                //transactionRepository.postNewTransaction(accessToken, newTransactionRequestBody)
//                remoteDataSource.getTickets("ONE_WAY", "JAKARTA", "DENPASAR", "12-01-2022", "12-01-2022")
//            }
//        } returns postOrderTicketResponse

        viewModel.postNewTransaction(accessToken, newTransactionRequestBody)

        assertNotNull(viewModel.newTransactionResponse)

        assertEquals(postOrderTicketResponse, viewModel.newTransactionResponse.getOrAwaitValue())
    }
}
