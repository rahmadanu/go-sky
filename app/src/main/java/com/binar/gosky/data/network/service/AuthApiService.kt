package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.auth.otp.OtpResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApiService {

    @GET(ApiEndPoints.GET_OTP)
    suspend fun getOtp(
        @Query("email") email: String
    ): OtpResponse
}