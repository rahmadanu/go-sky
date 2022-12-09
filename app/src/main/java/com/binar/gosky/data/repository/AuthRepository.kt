package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.AuthRemoteDataSource
import com.binar.gosky.data.network.model.auth.otp.OtpResponse
import javax.inject.Inject

interface AuthRepository {
    suspend fun getOtp(email: String): com.binar.gosky.wrapper.Resource<OtpResponse>
}

class AuthRepositoryImpl @Inject constructor(private val dataSource: AuthRemoteDataSource): AuthRepository {
    override suspend fun getOtp(email: String): com.binar.gosky.wrapper.Resource<OtpResponse> {
        return proceed {
            dataSource.getOtp(email)
        }
    }

    private suspend fun <T> proceed(coroutines: suspend () -> T): com.binar.gosky.wrapper.Resource<T> {
        return try {
            com.binar.gosky.wrapper.Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            com.binar.gosky.wrapper.Resource.Error(e)
        }
    }
}