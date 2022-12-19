package com.binar.gosky.presentation.ui.auth.login

import androidx.lifecycle.*
import com.binar.gosky.data.network.model.auth.login.LoginRegisterRequestResponse
import com.binar.gosky.data.network.model.auth.login.LoginRequestBody
import com.binar.gosky.data.repository.AuthRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.util.SingleLiveEvent
import com.binar.gosky.wrapper.Resource
import com.bumptech.glide.Glide.init
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository, private val userRepository: UserRepository): ViewModel() {

    private var _postLoginUserResponse = MutableLiveData<Resource<LoginRegisterRequestResponse>>()
    val postLoginUserResponse: LiveData<Resource<LoginRegisterRequestResponse>> get() = _postLoginUserResponse

    val loginStatus = SingleLiveEvent<Boolean>()

    init {
        getUserLoginStatus()
    }

    fun postLoginUser(loginRequestBody: LoginRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val loginResponse = authRepository.postLoginUser(loginRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postLoginUserResponse.postValue(loginResponse)
            }
        }
    }

    fun setUserLogin(isLogin: Boolean) {
        viewModelScope.launch {
            userRepository.setUserLogin(isLogin)
        }
    }

    private fun getUserLoginStatus(){
        viewModelScope.launch {
            userRepository.getUserLoginStatus().collectLatest {
                loginStatus.value = it
            }
        }
    }

    fun setUserAccessToken(accessToken: String) {
        viewModelScope.launch {
            userRepository.setUserAccessToken(accessToken)
        }
    }
}