package com.binar.gosky.data.repository

import com.binar.gosky.data.local.datasource.UserLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {
    suspend fun setUserLogin(isLogin: Boolean)

    fun getUserLogin(): Flow<Boolean>
}

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun setUserLogin(isLogin: Boolean) {
        return userLocalDataSource.setUserLogin(isLogin)
    }

    override fun getUserLogin(): Flow<Boolean> {
        return userLocalDataSource.getUserLogin()
    }

}