package com.binar.gosky.presentation.ui.auth.register

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.binar.gosky.MainDispatcherRule
import com.binar.gosky.data.network.datasource.AuthRemoteDataSourceImpl
import com.binar.gosky.data.network.model.auth.register.RegisterRequestBody
import com.binar.gosky.data.network.service.AuthApiService
import com.binar.gosky.data.repository.AuthRepository
import com.binar.gosky.data.repository.AuthRepositoryImpl
import com.binar.gosky.data.repository.UserRepository
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var repository: AuthRepositoryImpl

    @Mock
    private lateinit var authRepository: AuthRepository


    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = RegisterViewModel(repository)
    }

    @Test
    fun postRegister() = runTest {
        val postRegisterResponse = mock<Response<RegisterRequestBody>>()

        val request = RegisterRequestBody("muhammad farros", "", "", "000000")

        //given(repository.postRegisterUser(request))

        viewModel.postRegisterUser(request)
        advanceUntilIdle()

        Mockito.verify(repository).postRegisterUser(request)
        assertNotNull(viewModel.postRegisterUserResponse)
        assertEquals(viewModel.postRegisterUserResponse.value?.payload, postRegisterResponse)

    }

}
