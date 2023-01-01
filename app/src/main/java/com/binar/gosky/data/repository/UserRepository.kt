package com.binar.gosky.data.repository

import com.binar.gosky.data.local.datasource.UserLocalDataSource
import com.binar.gosky.data.network.datasource.UserRemoteDataSource
import com.binar.gosky.data.network.model.users.data.EditEmailUserRequestBody
import com.binar.gosky.data.network.model.users.data.EditUserResponse
import com.binar.gosky.data.network.model.users.data.EditUserRequestBody
import com.binar.gosky.data.network.model.users.password.NewPasswordResetRequestBody
import com.binar.gosky.data.network.model.users.password.NewPasswordResetResponse
import com.binar.gosky.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {
    suspend fun setUserLogin(isLogin: Boolean)
    fun getUserLoginStatus(): Flow<Boolean>

    suspend fun setUserAccessToken(accessToken: String)
    fun getUserAccessToken(): Flow<String>

    suspend fun putUserData(accessToken: String, editUserRequestBody: EditUserRequestBody): Resource<EditUserResponse>
    suspend fun putUserEmail(accessToken: String, editEmailUserRequestBody: EditEmailUserRequestBody): Resource<EditUserResponse>
    suspend fun putNewPasswordInResetPassword(accessToken: String,newPassword: NewPasswordResetRequestBody): Resource<NewPasswordResetResponse>
}

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun setUserLogin(isLogin: Boolean) {
        userLocalDataSource.setUserLogin(isLogin)
    }

    override fun getUserLoginStatus(): Flow<Boolean> {
        return userLocalDataSource.getUserLoginStatus()
    }

    override suspend fun setUserAccessToken(accessToken: String) {
        userLocalDataSource.setUserAccessToken(accessToken)
    }

    override fun getUserAccessToken(): Flow<String> {
        return userLocalDataSource.getUserAccessToken()
    }

    override suspend fun putUserData(accessToken: String, editUserRequestBody: EditUserRequestBody): Resource<EditUserResponse> {
        return proceed {
            userRemoteDataSource.putUserData(accessToken, editUserRequestBody)
        }
    }

    override suspend fun putUserEmail(
        accessToken: String,
        editEmailUserRequestBody: EditEmailUserRequestBody
    ): Resource<EditUserResponse> {
        return proceed {
            userRemoteDataSource.putUserEmail(accessToken, editEmailUserRequestBody)
        }
    }

    override suspend fun putNewPasswordInResetPassword(
        accessToken: String,
        newPassword: NewPasswordResetRequestBody
    ): Resource<NewPasswordResetResponse> {
        return proceed {
            userRemoteDataSource.putNewPasswordInResetPassword(accessToken, newPassword)
        }
    }

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e, e.message)
        }
    }
}