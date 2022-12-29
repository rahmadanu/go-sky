package com.binar.gosky.presentation.ui.notification

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.gosky.data.network.model.notification.NotificationData
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
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.mock

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

        val response = "123456"

        every {
            runBlocking {
                repository.getNotification(response)
            }
        } returns notificationResponse

        viewModel.getNotification(response)

        assertNotNull(viewModel.notificationResponse)
        //assertEquals(notificationResponse, viewModel.notificationResponse.getOrAwaitValue())
        assertEquals(notificationResponse, viewModel.notificationResponse.getOrAwaitValue())
    }

    @Test
    fun readNotification() {

    }

}
