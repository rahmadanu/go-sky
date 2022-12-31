package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.users.EditEmailUserRequestBody
import com.binar.gosky.data.network.model.users.EditUserResponse
import com.binar.gosky.data.network.model.users.EditUserRequestBody
import com.binar.gosky.data.network.service.UserApiService
import javax.inject.Inject

interface UserRemoteDataSource {
    suspend fun putUserData(accessToken: String, editUserRequestBody: EditUserRequestBody): EditUserResponse
    suspend fun putUserEmail(accessToken: String, editEmailUserRequestBody: EditEmailUserRequestBody): EditUserResponse
}

class UserRemoteDataSourceImpl @Inject constructor(private val apiService: UserApiService): UserRemoteDataSource {
    override suspend fun putUserData(accessToken: String, editUserRequestBody: EditUserRequestBody): EditUserResponse {
        return apiService.putUserData(accessToken, editUserRequestBody)
    }

    override suspend fun putUserEmail(
        accessToken: String,
        editEmailUserRequestBody: EditEmailUserRequestBody
    ): EditUserResponse {
        return apiService.putUserEmail(accessToken, editEmailUserRequestBody)
    }
}