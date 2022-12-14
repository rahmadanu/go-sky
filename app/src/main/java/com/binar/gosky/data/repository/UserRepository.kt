package com.binar.gosky.data.repository

import com.binar.gosky.data.local.datasource.UserLocalDataSource
import com.binar.gosky.data.network.datasource.UserRemoteDataSource
import com.binar.gosky.data.network.model.users.UserRequestBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {
    suspend fun setUserLogin(isLogin: Boolean)
    fun getUserLoginStatus(): Flow<Boolean>

    suspend fun setUserAccessToken(accessToken: String)
    fun getUserAccessToken(): Flow<String>

    suspend fun putUserData(accessToken: String, userRequestBody: UserRequestBody)
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

    override suspend fun putUserData(accessToken: String, userRequestBody: UserRequestBody) {
        userRemoteDataSource.putUserData(accessToken, userRequestBody)
    }

}