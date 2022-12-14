package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.users.UserRequestBody
import com.binar.gosky.data.network.service.UserApiService
import retrofit2.http.Body
import retrofit2.http.Header
import javax.inject.Inject

interface UserRemoteDataSource {
    suspend fun putUserData(accessToken: String, userRequestBody: UserRequestBody)
}

class UserRemoteDataSourceImpl @Inject constructor(private val apiService: UserApiService): UserRemoteDataSource {
    override suspend fun putUserData(accessToken: String, userRequestBody: UserRequestBody) {
        apiService.putUserData(accessToken, userRequestBody)
    }
}