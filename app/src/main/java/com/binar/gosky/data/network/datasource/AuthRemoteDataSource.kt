package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.auth.login.LoginRegisterRequestResponse
import com.binar.gosky.data.network.model.auth.login.LoginRequestBody
import com.binar.gosky.data.network.model.auth.otp.OtpResponse
import com.binar.gosky.data.network.model.auth.register.RegisterRequestBody
import com.binar.gosky.data.network.model.auth.user.CurrentUserResponse
import com.binar.gosky.data.network.service.ApiEndPoints
import com.binar.gosky.data.network.service.AuthApiService
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Inject

interface AuthRemoteDataSource {
    suspend fun getOtp(email: String): OtpResponse
    suspend fun getCurrentUser(accessToken: String): CurrentUserResponse

    suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): LoginRegisterRequestResponse
    suspend fun postLoginUser(loginRequestBody: LoginRequestBody): LoginRegisterRequestResponse
}

class AuthRemoteDataSourceImpl @Inject constructor(private val apiService: AuthApiService) :
    AuthRemoteDataSource {
    override suspend fun getOtp(email: String): OtpResponse {
        return apiService.getOtp(email)
    }

    override suspend fun getCurrentUser(accessToken: String): CurrentUserResponse {
        return apiService.getCurrentUser(accessToken)
    }

    override suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): LoginRegisterRequestResponse {
        return apiService.postRegisterUser(registerRequestBody)
    }

    override suspend fun postLoginUser(loginRequestBody: LoginRequestBody): LoginRegisterRequestResponse {
        return apiService.postLoginUser(loginRequestBody)
    }
}