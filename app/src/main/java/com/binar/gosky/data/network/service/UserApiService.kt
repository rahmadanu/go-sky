package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.users.UserRequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT

interface UserApiService {

    @PUT(ApiEndPoints.PUT_USER_DATA)
    suspend fun putUserData(
        @Header("Authorization") accessToken: String,
        @Body userRequestBody: UserRequestBody
    )

    @PUT(ApiEndPoints.PUT_USER_EMAIL)
    suspend fun putUserEmail(
        @Header("Authorization") accessToken: String,
    )
}