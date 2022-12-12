package com.binar.gosky.presentation.ui.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.gosky.data.network.model.auth.login.LoginRegisterRequestResponse
import com.binar.gosky.data.network.model.auth.login.LoginRequestBody
import com.binar.gosky.data.repository.AuthRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {

    private var _postLoginUserResponse = MutableLiveData<Resource<LoginRegisterRequestResponse>>()
    val postLoginUserResponse: LiveData<Resource<LoginRegisterRequestResponse>> get() = _postLoginUserResponse

    fun postLoginUser(loginRequestBody: LoginRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val loginResponse = repository.postLoginUser(loginRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postLoginUserResponse.postValue(loginResponse)
            }
        }
    }
}