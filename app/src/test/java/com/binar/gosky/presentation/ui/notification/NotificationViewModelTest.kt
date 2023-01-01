package com.binar.gosky.presentation.ui.notification

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.gosky.data.network.model.notification.NotificationResponse
import com.binar.gosky.data.repository.NotificationRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.getOrAwaitValue
import com.binar.gosky.wrapper.Resource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class NotificationViewModelTest {
    private lateinit var viewModel: NotificationViewModel
    private lateinit var repository: NotificationRepository
    private lateinit var userRepository: UserRepository
    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = mockk()
        userRepository = mockk()
        viewModel = NotificationViewModel(repository, userRepository)

    }

    @Test
    fun getNotification() {
        val notificationResponse = mockk<Resource<NotificationResponse>>()

        val accessToken =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NCwibmFtZSI6ImFkbWluIiwiZW1haWwiOiJnb3NreS5hZG1pbkBnbWFpbC5jb20iLCJlbmNyeXB0ZWRQYXNzd29yZCI6IiQyYSQxMCROQ0wzYVZkd00wY09ZTVltQk1yRnMuRnRXQlJuaUxpUmU5UlpWLkhTU1BYRjMwcDhCTEFRbSIsInJvbGUiOiJBRE1JTiIsInBob25lIjoiMDgxMjM0MzEyMzQzIiwiYWRkcmVzcyI6Impha2FydGEiLCJpbWFnZUlkIjoiNDExMzExMzEiLCJpbWFnZVVybCI6Imh0dHBzOi8vd3d3LnNodXR0ZXJzdG9jay5jb20vaW1hZ2UtdmVjdG9yL3RocmVlLXBlcnNvbnMtYWRtaW4taWNvbi1vdXRsaW5lLTYwMHctMTczMDk3NDE2NS5qcGciLCJkZWxldGVkQXQiOm51bGwsImNyZWF0ZWRBdCI6IjIwMjItMTItMzBUMDY6MzI6MjcuNzM1WiIsInVwZGF0ZWRBdCI6IjIwMjItMTItMzBUMDY6MzI6MjcuNzM1WiIsImlhdCI6MTY3MjM4MzE4OH0.HmfoJSE0QUc5DEFlFAXCj50xREMfB2rWJinW6NWBUM0"


        every {
            runBlocking {
                repository.getNotification(accessToken)
            }
        } returns notificationResponse

        viewModel.getNotification(accessToken)

        assertNotNull(viewModel.notificationResponse)
        assertEquals(
            notificationResponse,
            viewModel.notificationResponse.getOrAwaitValue() as Resource
        )
    }

    @Test
    fun readNotification() {
        val unitResponse = mockk<Unit>()

        val accessToken =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NCwibmFtZSI6ImFkbWluIiwiZW1haWwiOiJnb3NreS5hZG1pbkBnbWFpbC5jb20iLCJlbmNyeXB0ZWRQYXNzd29yZCI6IiQyYSQxMCROQ0wzYVZkd00wY09ZTVltQk1yRnMuRnRXQlJuaUxpUmU5UlpWLkhTU1BYRjMwcDhCTEFRbSIsInJvbGUiOiJBRE1JTiIsInBob25lIjoiMDgxMjM0MzEyMzQzIiwiYWRkcmVzcyI6Impha2FydGEiLCJpbWFnZUlkIjoiNDExMzExMzEiLCJpbWFnZVVybCI6Imh0dHBzOi8vd3d3LnNodXR0ZXJzdG9jay5jb20vaW1hZ2UtdmVjdG9yL3RocmVlLXBlcnNvbnMtYWRtaW4taWNvbi1vdXRsaW5lLTYwMHctMTczMDk3NDE2NS5qcGciLCJkZWxldGVkQXQiOm51bGwsImNyZWF0ZWRBdCI6IjIwMjItMTItMzBUMDY6MzI6MjcuNzM1WiIsInVwZGF0ZWRBdCI6IjIwMjItMTItMzBUMDY6MzI6MjcuNzM1WiIsImlhdCI6MTY3MjM4MzE4OH0.HmfoJSE0QUc5DEFlFAXCj50xREMfB2rWJinW6NWBUM0"

        val id = 1

        every {
            runBlocking {
                repository.putNotificationRead(accessToken, id)
            }
        } returns unitResponse

        viewModel.putNotificationRead(accessToken, id)
        assertNotNull(viewModel.putNotificationRead(accessToken, id))
        assertEquals(unitResponse, viewModel.putNotificationRead(accessToken, id))
    }

}
