package com.binar.gosky.data.local.datasource

import com.binar.gosky.data.local.preference.UserDataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserLocalDataSource {
    suspend fun setUserLogin(isLogin: Boolean)

    fun getUserLogin(): Flow<Boolean>
}

class UserLocalDataSourceImpl @Inject constructor(
    private val userDataStore: UserDataStoreManager
): UserLocalDataSource {

    override suspend fun setUserLogin(isLogin: Boolean) {
        userDataStore.setUserLogin(isLogin)
    }

    override fun getUserLogin(): Flow<Boolean> {
        return userDataStore.getUserLogin()
    }
}