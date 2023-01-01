package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.auth.login.LoginRegisterRequestResponse
import com.binar.gosky.data.network.model.auth.login.LoginRequestBody
import com.binar.gosky.data.network.model.auth.otp.OtpResponse
import com.binar.gosky.data.network.model.auth.password.NewPasswordRequestBody
import com.binar.gosky.data.network.model.auth.password.NewPasswordResponse
import com.binar.gosky.data.network.model.auth.register.RegisterRequestBody
import com.binar.gosky.data.network.model.auth.user.CurrentUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AuthApiService {

    @GET(ApiEndPoints.GET_OTP)
    suspend fun getOtp(
        @Query("email") email: String
    ): OtpResponse

    @GET(ApiEndPoints.GET_WHO_AM_I)
    suspend fun getCurrentUser(
        @Header("Authorization") accessToken: String,
    ): CurrentUserResponse

    @POST(ApiEndPoints.POST_REGISTER)
    suspend fun postRegisterUser(
        @Body registerRequestBody: RegisterRequestBody
    ): LoginRegisterRequestResponse

    @POST(ApiEndPoints.POST_LOGIN)
    suspend fun postLoginUser(
        @Body loginRequestBody: LoginRequestBody
    ): LoginRegisterRequestResponse

    @PUT(ApiEndPoints.PUT_PASSWORD)
    suspend fun putNewPassword(
        @Body newPassword: NewPasswordRequestBody
    ): NewPasswordResponse
}