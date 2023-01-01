package com.binar.gosky.presentation.ui.auth.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.gosky.data.network.model.auth.login.LoginRegisterRequestResponse
import com.binar.gosky.data.network.model.auth.register.RegisterRequestBody
import com.binar.gosky.data.repository.AuthRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.getOrAwaitValue
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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.rules.TestRule
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class RegisterViewModelTest {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository
    private val dispatcher = TestCoroutineDispatcher()


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        authRepository = mockk()
        userRepository = mockk()
        viewModel = RegisterViewModel(authRepository, userRepository)
    }

    @Test
    fun postRegister() {
        val postRegisterResource =
            mock<com.binar.gosky.wrapper.Resource<LoginRegisterRequestResponse>>()

        val request = RegisterRequestBody("muhammad farros", "123456", "123456", "000000")

        every {
            runBlocking {
                authRepository.postRegisterUser(request)
            }
        } returns postRegisterResource

        viewModel.postRegisterUser(request)

        assertNotNull(viewModel.postRegisterUserResponse)
        assertEquals(postRegisterResource, viewModel.postRegisterUserResponse.getOrAwaitValue())
    }

}
