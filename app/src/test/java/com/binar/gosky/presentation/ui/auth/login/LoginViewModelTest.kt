package com.binar.gosky.presentation.ui.auth.login

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.binar.gosky.MainDispatcherRule
import com.binar.gosky.data.local.datasource.UserLocalDataSourceImpl
import com.binar.gosky.data.local.preference.UserDataStoreManager
import com.binar.gosky.data.network.datasource.AuthRemoteDataSourceImpl
import com.binar.gosky.data.network.model.auth.login.LoginRequestBody
import com.binar.gosky.data.network.service.AuthApiService
import com.binar.gosky.data.repository.AuthRepositoryImpl
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.data.repository.UserRepositoryImpl
import com.binar.gosky.presentation.ui.auth.register.RegisterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: AuthRepositoryImpl
    private lateinit var apiService: AuthApiService
    private lateinit var remoteDataSource: AuthRemoteDataSourceImpl
    private lateinit var userRepository: UserRepository
    private lateinit var userLocalDataSource: UserLocalDataSourceImpl
    private lateinit var userDataStoreManager: UserDataStoreManager

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        apiService = mock()
        remoteDataSource = AuthRemoteDataSourceImpl(apiService)
        repository = AuthRepositoryImpl(remoteDataSource)
        userDataStoreManager = UserDataStoreManager(context)
        userLocalDataSource = UserLocalDataSourceImpl(userDataStoreManager)
        userRepository = UserRepositoryImpl(userLocalDataSource)
        viewModel = LoginViewModel(repository, userRepository)
    }

    @Test
    fun postLogin() = runTest {
        val postLoginResponse = mock<Response<LoginRequestBody>>()

        val request = LoginRequestBody("admin@mail", "admin")

        // given(repository.postLoginUser(request)).willReturn(postLoginResponse)

        viewModel.postLoginUser(request)

        verify(repository, Mockito.times(1)).postLoginUser(request)
        assertNotNull(viewModel.postLoginUserResponse)
        assertEquals(viewModel.postLoginUserResponse.value?.payload, postLoginResponse)
    }

}
