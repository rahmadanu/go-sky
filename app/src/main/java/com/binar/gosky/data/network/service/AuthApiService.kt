package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.auth.login.LoginRegisterRequestResponse
import com.binar.gosky.data.network.model.auth.login.LoginRequestBody
import com.binar.gosky.data.network.model.auth.otp.OtpResponse
import com.binar.gosky.data.network.model.auth.register.RegisterRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @GET(ApiEndPoints.GET_OTP)
    suspend fun getOtp(
        @Query("email") email: String
    ): OtpResponse

    @POST(ApiEndPoints.POST_REGISTER)
    suspend fun postRegisterUser(
        @Body registerRequestBody: RegisterRequestBody
    ): LoginRegisterRequestResponse

    @POST(ApiEndPoints.POST_LOGIN)
    suspend fun postLoginUser(
        @Body loginRequestBody: LoginRequestBody
    ): LoginRegisterRequestResponse
}