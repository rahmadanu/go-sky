package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.auth.otp.OtpResponse
import com.binar.gosky.data.network.service.ApiEndPoints
import com.binar.gosky.data.network.service.AuthApiService
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface AuthRemoteDataSource {
    suspend fun getOtp(email: String): OtpResponse
}

class AuthRemoteDataSourceImpl @Inject constructor(private val apiService: AuthApiService): AuthRemoteDataSource {
    override suspend fun getOtp(email: String): OtpResponse {
        return apiService.getOtp(email)
    }
}