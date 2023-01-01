package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.users.data.UserByIdResponse
import com.binar.gosky.data.network.model.users.edit.EditEmailUserRequestBody
import com.binar.gosky.data.network.model.users.edit.EditUserRequestBody
import com.binar.gosky.data.network.model.users.edit.EditUserResponse
import com.binar.gosky.data.network.model.users.password.NewPasswordResetRequestBody
import com.binar.gosky.data.network.model.users.password.NewPasswordResetResponse
import com.binar.gosky.data.network.service.UserApiService
import javax.inject.Inject

interface UserRemoteDataSource {
    suspend fun putUserData(accessToken: String, editUserRequestBody: EditUserRequestBody): EditUserResponse
    suspend fun putUserEmail(accessToken: String, editEmailUserRequestBody: EditEmailUserRequestBody): EditUserResponse
    suspend fun getUserById( userId: Int): UserByIdResponse
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

    override suspend fun getUserById(userId: Int): UserByIdResponse {
        return apiService.getUserById(userId)
    }

    override suspend fun putNewPasswordInResetPassword(
        accessToken: String,
        newPassword: NewPasswordResetRequestBody
    ): NewPasswordResetResponse {
        return apiService.putNewPasswordInResetPassword(accessToken, newPassword)
    }
}