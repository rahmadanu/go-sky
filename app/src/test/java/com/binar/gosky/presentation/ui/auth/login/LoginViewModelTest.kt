package com.binar.gosky.presentation.ui.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.gosky.data.network.model.auth.login.LoginRegisterRequestResponse
import com.binar.gosky.data.network.model.auth.login.LoginRequestBody
import com.binar.gosky.data.repository.AuthRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
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

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: AuthRepository
    private lateinit var userRepository: UserRepository
    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = mockk()
        userRepository = mockk()
        viewModel = LoginViewModel(repository, userRepository)
    }

    @Test
    fun postLogin() {
        val postLoginResponse = mockk<Resource<LoginRegisterRequestResponse>>()

        val request = LoginRequestBody("admin@mail", "admin")

        every {
            runBlocking {
                repository.postLoginUser(request)
            }
        } returns postLoginResponse

        viewModel.postLoginUser(request)

        assertNotNull(viewModel.postLoginUserResponse)
        assertEquals(postLoginResponse, viewModel.postLoginUserResponse.getOrAwaitValue())
    }

}
