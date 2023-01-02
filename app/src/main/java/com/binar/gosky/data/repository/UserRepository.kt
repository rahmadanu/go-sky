package com.binar.gosky.data.repository

import com.binar.gosky.data.local.datasource.UserLocalDataSource
import com.binar.gosky.data.network.datasource.UserRemoteDataSource
import com.binar.gosky.data.network.model.users.data.UserByIdResponse
import com.binar.gosky.data.network.model.users.edit.EditEmailUserRequestBody
import com.binar.gosky.data.network.model.users.edit.EditUserRequestBody
import com.binar.gosky.util.proceed
import com.binar.gosky.data.network.model.users.edit.EditUserResponse
import com.binar.gosky.data.network.model.error.ErrorResponse
import com.binar.gosky.data.network.model.users.password.NewPasswordResetRequestBody
import com.binar.gosky.data.network.model.users.password.NewPasswordResetResponse
import com.binar.gosky.wrapper.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

interface UserRepository {
    suspend fun setUserLogin(isLogin: Boolean)
    fun getUserLoginStatus(): Flow<Boolean>

    suspend fun setUserAccessToken(accessToken: String)
    fun getUserAccessToken(): Flow<String>

    suspend fun putUserData(accessToken: String, editUserRequestBody: EditUserRequestBody): Resource<EditUserResponse>
    suspend fun putUserEmail(accessToken: String, editEmailUserRequestBody: EditEmailUserRequestBody): Resource<EditUserResponse>
    suspend fun putNewPasswordInResetPassword(accessToken: String,newPassword: NewPasswordResetRequestBody): Resource<NewPasswordResetResponse>
    suspend fun setUserRole(role: String)

    //fun checkIfUserIsAdmin(): Boolean
    fun getUserRole(): Flow<String>
    suspend fun getUserById( userId: Int): Resource<UserByIdResponse>
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

    override suspend fun setUserRole(role: String) {
        userLocalDataSource.setUserRole(role)
    }

    override fun getUserRole(): Flow<String> {
        return userLocalDataSource.getUserRole()
    }

    /*    override fun checkIfUserIsAdmin(): Boolean {
            val role = userLocalDataSource.getUserRole().asLiveData().value
            return role.equals("ADMIN")
        }*/

    override suspend fun putUserEmail(
        accessToken: String,
        editEmailUserRequestBody: EditEmailUserRequestBody
    ): Resource<EditUserResponse> {
        return proceed {
            userRemoteDataSource.putUserEmail(accessToken, editEmailUserRequestBody)
        }
    }

    override suspend fun getUserById(userId: Int): Resource<UserByIdResponse> {
        return proceed {
            userRemoteDataSource.getUserById(userId)
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
}