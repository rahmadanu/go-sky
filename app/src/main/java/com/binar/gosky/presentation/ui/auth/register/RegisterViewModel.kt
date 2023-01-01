package com.binar.gosky.presentation.ui.auth.register

import android.util.Log
import androidx.lifecycle.*
import com.binar.gosky.data.network.model.auth.login.LoginRegisterRequestResponse
import com.binar.gosky.data.network.model.auth.otp.OtpResponse
import com.binar.gosky.data.network.model.auth.register.RegisterRequestBody
import com.binar.gosky.data.repository.AuthRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository, private val userRepository: UserRepository) : ViewModel() {

    private var _otpResponse = MutableLiveData<Resource<OtpResponse>>()
    val otpResponse: LiveData<Resource<OtpResponse>> get() = _otpResponse

    private var _postRegisterUserResponse = MutableLiveData<Resource<LoginRegisterRequestResponse>>()
    val postRegisterUserResponse: LiveData<Resource<LoginRegisterRequestResponse>> get() = _postRegisterUserResponse

    fun getOtp(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val otp = authRepository.getOtp(email)
            viewModelScope.launch(Dispatchers.Main) {
                _otpResponse.postValue(otp)
            }
        }
    }

    fun postRegisterUser(registerRequestBody: RegisterRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val registerResponse = authRepository.postRegisterUser(registerRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postRegisterUserResponse.postValue(registerResponse)
            }
        }
    }

    fun setUserAccessToken(accessToken: String) {
        viewModelScope.launch {
            Log.d("setuseraccesstoken", accessToken)
            userRepository.setUserAccessToken(accessToken)
        }
    }

    fun getUserAccessToken(): LiveData<String> {
        return userRepository.getUserAccessToken().asLiveData()
    }
}