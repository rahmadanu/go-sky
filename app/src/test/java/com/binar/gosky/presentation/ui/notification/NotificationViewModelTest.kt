package com.binar.gosky.presentation.ui.notification

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.gosky.data.network.model.notification.NotificationResponse
import com.binar.gosky.data.repository.NotificationRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.rule.MainCoroutineRule
import com.binar.gosky.wrapper.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestRule
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class NotificationViewModelTest {
    private lateinit var viewModel: NotificationViewModel
    private lateinit var notificationRepository: NotificationRepository
    private lateinit var userRepository: UserRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        notificationRepository = mock()
        userRepository = mock()
        viewModel = NotificationViewModel(notificationRepository, userRepository)
    }

    @Test
    fun getNotification(){
        val notificationResponse = mock<Resource<NotificationResponse>>()

        //given(notificationRepository.getNotification("123456")).willReturn(notificationResponse)
    }

    @Test
    fun readNotification(){

    }

}
