package com.binar.gosky.presentation.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.repository.TicketsRepository
import com.binar.gosky.wrapper.Resource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class SearchResultViewModelTest {

    private lateinit var viewModel: SearchResultViewModel
    private lateinit var repository: TicketsRepository
    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = mockk()
        repository = mockk()
        viewModel = SearchResultViewModel(repository)
    }

    @Test
    fun getTicket() {
        val getTicketResponse = mockk<Resource<Tickets>>()

        val accessToken =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NCwibmFtZSI6ImFkbWluIiwiZW1haWwiOiJnb3NreS5hZG1pbkBnbWFpbC5jb20iLCJlbmNyeXB0ZWRQYXNzd29yZCI6IiQyYSQxMCROQ0wzYVZkd00wY09ZTVltQk1yRnMuRnRXQlJuaUxpUmU5UlpWLkhTU1BYRjMwcDhCTEFRbSIsInJvbGUiOiJBRE1JTiIsInBob25lIjoiMDgxMjM0MzEyMzQzIiwiYWRkcmVzcyI6Impha2FydGEiLCJpbWFnZUlkIjoiNDExMzExMzEiLCJpbWFnZVVybCI6Imh0dHBzOi8vd3d3LnNodXR0ZXJzdG9jay5jb20vaW1hZ2UtdmVjdG9yL3RocmVlLXBlcnNvbnMtYWRtaW4taWNvbi1vdXRsaW5lLTYwMHctMTczMDk3NDE2NS5qcGciLCJkZWxldGVkQXQiOm51bGwsImNyZWF0ZWRBdCI6IjIwMjItMTItMzBUMDY6MzI6MjcuNzM1WiIsInVwZGF0ZWRBdCI6IjIwMjItMTItMzBUMDY6MzI6MjcuNzM1WiIsImlhdCI6MTY3MjM4MzE4OH0.HmfoJSE0QUc5DEFlFAXCj50xREMfB2rWJinW6NWBUM0"

        every {
            runBlocking {
                repository.getTicketById(accessToken, 5)
            }
        } returns getTicketResponse

        viewModel.getTickets(
            "ONE_WAY",
            "JAKARTA",
            "MEDAN",
            "12-01-2023",
            "12-11-2023"
        )
    }

}
