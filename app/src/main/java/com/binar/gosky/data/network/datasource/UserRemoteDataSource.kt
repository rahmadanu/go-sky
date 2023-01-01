package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.users.data.EditEmailUserRequestBody
import com.binar.gosky.data.network.model.users.data.EditUserResponse
import com.binar.gosky.data.network.model.users.data.EditUserRequestBody
import com.binar.gosky.data.network.model.users.password.NewPasswordResetRequestBody
import com.binar.gosky.data.network.model.users.password.NewPasswordResetResponse
import com.binar.gosky.data.network.service.ApiEndPoints
import com.binar.gosky.data.network.service.UserApiService
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import javax.inject.Inject

interface UserRemoteDataSource {
    suspend fun putUserData(accessToken: String, editUserRequestBody: EditUserRequestBody): EditUserResponse
    suspend fun putUserEmail(accessToken: String, editEmailUserRequestBody: EditEmailUserRequestBody): EditUserResponse
    suspend fun putNewPasswordInResetPassword(accessToken: String,newPassword: NewPasswordResetRequestBody): NewPasswordResetResponse
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

    override suspend fun putNewPasswordInResetPassword(
        accessToken: String,
        newPassword: NewPasswordResetRequestBody
    ): NewPasswordResetResponse {
        return apiService.putNewPasswordInResetPassword(accessToken, newPassword)
    }
}