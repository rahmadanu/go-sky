package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.users.data.UserByIdResponse
import com.binar.gosky.data.network.model.users.edit.EditEmailUserRequestBody
import com.binar.gosky.data.network.model.users.edit.EditUserResponse
import com.binar.gosky.data.network.model.users.password.NewPasswordResetRequestBody
import com.binar.gosky.data.network.model.users.password.NewPasswordResetResponse
import retrofit2.http.*

interface UserApiService {

    @PUT(ApiEndPoints.PUT_USER_DATA)
    suspend fun putUserData(
        @Header("Authorization") accessToken: String,
        @Body editUserRequestBody: com.binar.gosky.data.network.model.users.edit.EditUserRequestBody
    ): EditUserResponse

    @PUT(ApiEndPoints.PUT_USER_EMAIL)
    suspend fun putUserEmail(
        @Header("Authorization") accessToken: String,
        @Body editEmailUserRequestBody: EditEmailUserRequestBody
    ): EditUserResponse

    @GET(ApiEndPoints.GET_USER_BY_ID)
    suspend fun getUserById(
        @Path("id") userId: Int
    ): UserByIdResponse

    @PUT(ApiEndPoints.PUT_PASSWORD_RESET)
    suspend fun putNewPasswordInResetPassword(
        @Header("Authorization") accessToken: String,
        @Body newPassword: NewPasswordResetRequestBody
    ): NewPasswordResetResponse
}