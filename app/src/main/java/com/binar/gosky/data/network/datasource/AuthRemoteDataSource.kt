package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.auth.login.LoginRegisterRequestResponse
import com.binar.gosky.data.network.model.auth.otp.OtpResponse
import com.binar.gosky.data.network.model.auth.register.RegisterRequestBody
import com.binar.gosky.data.network.service.AuthApiService
import retrofit2.http.Body
import javax.inject.Inject

interface AuthRemoteDataSource {
    suspend fun getOtp(email: String): OtpResponse
    suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): LoginRegisterRequestResponse
}

class AuthRemoteDataSourceImpl @Inject constructor(private val apiService: AuthApiService) :
    AuthRemoteDataSource {
    override suspend fun getOtp(email: String): OtpResponse {
        return apiService.getOtp(email)
    }

    override suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): LoginRegisterRequestResponse {
        return apiService.postRegisterUser(registerRequestBody)
    }
}