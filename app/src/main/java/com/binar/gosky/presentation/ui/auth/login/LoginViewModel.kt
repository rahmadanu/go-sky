package com.binar.gosky.presentation.ui.auth.login

import androidx.lifecycle.*
import com.binar.gosky.data.network.model.auth.login.LoginRegisterRequestResponse
import com.binar.gosky.data.network.model.auth.login.LoginRequestBody
import com.binar.gosky.data.repository.AuthRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository, private val userRepository: UserRepository): ViewModel() {

    private var _postLoginUserResponse = MutableLiveData<Resource<LoginRegisterRequestResponse>>()
    val postLoginUserResponse: LiveData<Resource<LoginRegisterRequestResponse>> get() = _postLoginUserResponse

    fun postLoginUser(loginRequestBody: LoginRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val loginResponse = authRepository.postLoginUser(loginRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postLoginUserResponse.postValue(loginResponse)
            }
        }
    }

    suspend fun setUserLogin(isLogin: Boolean) {
        viewModelScope.launch {
            userRepository.setUserLogin(isLogin)
        }
    }

    fun getUserLoginStatus(): LiveData<Boolean> {
        return userRepository.getUserLoginStatus().asLiveData()
    }
}