package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.AuthRemoteDataSource
import com.binar.gosky.data.network.model.auth.login.LoginRegisterRequestResponse
import com.binar.gosky.data.network.model.auth.login.LoginRequestBody
import com.binar.gosky.data.network.model.auth.otp.OtpResponse
import com.binar.gosky.data.network.model.auth.password.NewPasswordRequestBody
import com.binar.gosky.data.network.model.auth.password.NewPasswordResponse
import com.binar.gosky.data.network.model.auth.register.RegisterRequestBody
import com.binar.gosky.data.network.model.auth.user.CurrentUserResponse
import com.binar.gosky.util.proceed
import com.binar.gosky.wrapper.Resource
import retrofit2.HttpException
import javax.inject.Inject

interface AuthRepository {
    suspend fun getOtp(email: String): Resource<OtpResponse>
    suspend fun getCurrentUser(accessToken: String): Resource<CurrentUserResponse>

    suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): Resource<LoginRegisterRequestResponse>
    suspend fun postLoginUser(loginRequestBody: LoginRequestBody): Resource<LoginRegisterRequestResponse>
    suspend fun putNewPasswordInForgotPassword(newPassword: NewPasswordRequestBody): Resource<NewPasswordResponse>
}

class AuthRepositoryImpl @Inject constructor(private val dataSource: AuthRemoteDataSource) :
    AuthRepository {
    override suspend fun getOtp(email: String): Resource<OtpResponse> {
        return proceed {
            dataSource.getOtp(email)
        }
    }

    override suspend fun getCurrentUser(accessToken: String): Resource<CurrentUserResponse> {
        return proceed {
            dataSource.getCurrentUser(accessToken)
        }
    }

    override suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): Resource<LoginRegisterRequestResponse> {
        return proceed {
            dataSource.postRegisterUser(registerRequestBody)
        }
    }

    override suspend fun postLoginUser(loginRequestBody: LoginRequestBody): Resource<LoginRegisterRequestResponse> {
        return proceed {
            dataSource.postLoginUser(loginRequestBody)
        }
    }

    override suspend fun putNewPasswordInForgotPassword(newPassword: NewPasswordRequestBody): Resource<NewPasswordResponse> {
        return proceed {
            dataSource.putNewPasswordInForgotPassword(newPassword)
        }
    }
}