package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.users.data.UserByIdResponse
import com.binar.gosky.data.network.model.users.edit.EditEmailUserRequestBody
import com.binar.gosky.data.network.model.users.edit.EditEmailUserResponse
import com.binar.gosky.data.network.model.users.edit.EditUserRequestBody
import com.binar.gosky.data.network.service.UserApiService
import retrofit2.http.Path
import javax.inject.Inject

interface UserRemoteDataSource {
    suspend fun putUserData(accessToken: String, editUserRequestBody: EditUserRequestBody)
    suspend fun putUserEmail(accessToken: String, editEmailUserRequestBody: EditEmailUserRequestBody): EditEmailUserResponse
    suspend fun getUserById( userId: Int): UserByIdResponse
}

class UserRemoteDataSourceImpl @Inject constructor(private val apiService: UserApiService): UserRemoteDataSource {
    override suspend fun putUserData(accessToken: String, editUserRequestBody: EditUserRequestBody) {
        apiService.putUserData(accessToken, editUserRequestBody)
    }

    override suspend fun putUserEmail(
        accessToken: String,
        editEmailUserRequestBody: EditEmailUserRequestBody
    ): EditEmailUserResponse {
        return apiService.putUserEmail(accessToken, editEmailUserRequestBody)
    }

    override suspend fun getUserById(userId: Int): UserByIdResponse {
        return apiService.getUserById(userId)
    }
}