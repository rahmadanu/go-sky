package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.AuthRemoteDataSource
import com.binar.gosky.data.network.model.auth.login.LoginRegisterRequestResponse
import com.binar.gosky.data.network.model.auth.login.LoginRequestBody
import com.binar.gosky.data.network.model.auth.otp.OtpResponse
import com.binar.gosky.data.network.model.auth.register.RegisterRequestBody
import com.binar.gosky.wrapper.Resource
import retrofit2.http.Body
import javax.inject.Inject

interface AuthRepository {
    suspend fun getOtp(email: String): Resource<OtpResponse>
    suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): Resource<LoginRegisterRequestResponse>
    suspend fun postLoginUser(loginRequestBody: LoginRequestBody): Resource<LoginRegisterRequestResponse>
}

class AuthRepositoryImpl @Inject constructor(private val dataSource: AuthRemoteDataSource) :
    AuthRepository {
    override suspend fun getOtp(email: String): Resource<OtpResponse> {
        return proceed {
            dataSource.getOtp(email)
        }
    }

    override suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): Resource<LoginRegisterRequestResponse> {
        return proceed {
            dataSource.postRegisterUser(registerRequestBody)
        }
    }

    override suspend fun postLoginUser(loginRequestBody: LoginRequestBody): Resource<LoginRegisterRequestResponse> {
        return proceed {
            dataSource.postLoginUser(loginRequestBody)
        }
    }

    private suspend fun <T> proceed(coroutines: suspend () -> T): com.binar.gosky.wrapper.Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}