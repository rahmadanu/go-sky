package com.binar.gosky.presentation.ui.auth.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.gosky.data.network.model.auth.password.NewPasswordRequestBody
import com.binar.gosky.data.network.model.auth.password.NewPasswordResponse
import com.binar.gosky.data.network.model.users.password.NewPasswordResetRequestBody
import com.binar.gosky.data.network.model.users.password.NewPasswordResetResponse
import com.binar.gosky.data.repository.AuthRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _newPasswordResponse = MutableLiveData<Resource<NewPasswordResponse>>()
    val newPasswordResponse: LiveData<Resource<NewPasswordResponse>> get() = _newPasswordResponse

    private val _newPasswordResetResponse = MutableLiveData<Resource<NewPasswordResetResponse>>()
    val newPasswordResetResponse: LiveData<Resource<NewPasswordResetResponse>> get() = _newPasswordResetResponse

    fun putNewPasswordInForgotPassword(newPassword: NewPasswordRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = authRepository.putNewPasswordInForgotPassword(newPassword)
            viewModelScope.launch(Dispatchers.Main) {
                _newPasswordResponse.postValue(response)
            }
        }
    }

    fun putNewPasswordInResetPassword(accessToken: String,newPassword: NewPasswordResetRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository.putNewPasswordInResetPassword(accessToken, newPassword)
            viewModelScope.launch(Dispatchers.Main) {
                _newPasswordResetResponse.postValue(response)
            }
        }
    }

}