package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.users.data.UserByIdResponse
import com.binar.gosky.data.network.model.users.edit.EditEmailUserRequestBody
import com.binar.gosky.data.network.model.users.edit.EditEmailUserResponse
import com.binar.gosky.data.network.model.users.edit.EditUserRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {

    @PUT(ApiEndPoints.PUT_USER_DATA)
    suspend fun putUserData(
        @Header("Authorization") accessToken: String,
        @Body editUserRequestBody: EditUserRequestBody
    )

    @PUT(ApiEndPoints.PUT_USER_EMAIL)
    suspend fun putUserEmail(
        @Header("Authorization") accessToken: String,
        @Body editEmailUserRequestBody: EditEmailUserRequestBody
    ): EditEmailUserResponse

    @GET(ApiEndPoints.GET_USER_BY_ID)
    suspend fun getUserById(
        @Path("id") userId: Int
    ): UserByIdResponse
}