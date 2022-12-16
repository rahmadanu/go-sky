package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.users.EditEmailUserRequestBody
import com.binar.gosky.data.network.model.users.EditEmailUserResponse
import com.binar.gosky.data.network.model.users.EditUserRequestBody
import com.binar.gosky.data.network.service.UserApiService
import retrofit2.http.Body
import retrofit2.http.Header
import javax.inject.Inject

interface UserRemoteDataSource {
    suspend fun putUserData(accessToken: String, editUserRequestBody: EditUserRequestBody)
    suspend fun putUserEmail(accessToken: String, editEmailUserRequestBody: EditEmailUserRequestBody): EditEmailUserResponse
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
}