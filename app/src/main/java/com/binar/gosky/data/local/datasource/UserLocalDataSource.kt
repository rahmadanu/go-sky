package com.binar.gosky.data.local.datasource

import com.binar.gosky.data.local.preference.UserDataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserLocalDataSource {
    suspend fun setUserLogin(isLogin: Boolean)
    fun getUserLoginStatus(): Flow<Boolean>

    suspend fun setUserAccessToken(accessToken: String)
    fun getUserAccessToken(): Flow<String>

    suspend fun setUserRole(role: String)
    fun getUserRole(): Flow<String>
}

class UserLocalDataSourceImpl @Inject constructor(
    private val userDataStore: UserDataStoreManager
) : UserLocalDataSource {

    override suspend fun setUserLogin(isLogin: Boolean) {
        userDataStore.setUserLogin(isLogin)
    }

    override fun getUserLoginStatus(): Flow<Boolean> {
        return userDataStore.getUserLoginStatus()
    }

    override suspend fun setUserAccessToken(accessToken: String) {
        userDataStore.setUserAccessToken(accessToken)
    }

    override fun getUserAccessToken(): Flow<String> {
        return userDataStore.getUserAccessToken()
    }

    override suspend fun setUserRole(role: String) {
        userDataStore.setUserRole(role)
    }

    override fun getUserRole(): Flow<String> {
        return userDataStore.getUserRole()
    }
}