package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.auth.password.NewPasswordResponse
import com.binar.gosky.data.network.model.users.data.EditEmailUserRequestBody
import com.binar.gosky.data.network.model.users.data.EditUserResponse
import com.binar.gosky.data.network.model.users.data.EditUserRequestBody
import com.binar.gosky.data.network.model.users.password.NewPasswordResetRequestBody
import com.binar.gosky.data.network.model.users.password.NewPasswordResetResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT

interface UserApiService {

    @PUT(ApiEndPoints.PUT_USER_DATA)
    suspend fun putUserData(
        @Header("Authorization") accessToken: String,
        @Body editUserRequestBody: EditUserRequestBody
    ): EditUserResponse

    @PUT(ApiEndPoints.PUT_USER_EMAIL)
    suspend fun putUserEmail(
        @Header("Authorization") accessToken: String,
        @Body editEmailUserRequestBody: EditEmailUserRequestBody
    ): EditUserResponse

    @PUT(ApiEndPoints.PUT_PASSWORD_RESET)
    suspend fun putNewPasswordInResetPassword(
        @Header("Authorization") accessToken: String,
        @Body newPassword: NewPasswordResetRequestBody
    ): NewPasswordResetResponse
}