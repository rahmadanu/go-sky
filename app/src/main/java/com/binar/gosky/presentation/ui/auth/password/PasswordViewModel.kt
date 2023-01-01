package com.binar.gosky.presentation.ui.auth.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.gosky.data.network.model.auth.password.NewPasswordRequestBody
import com.binar.gosky.data.network.model.auth.password.NewPasswordResponse
import com.binar.gosky.data.repository.AuthRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {

    private val _newPasswordResponse = MutableLiveData<Resource<NewPasswordResponse>>()
    val newPasswordResponse: LiveData<Resource<NewPasswordResponse>> get() = _newPasswordResponse

    fun putNewPassword(newPassword: NewPasswordRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = authRepository.putNewPassword(newPassword)
            viewModelScope.launch(Dispatchers.Main) {
                _newPasswordResponse.postValue(response)
            }
        }
    }
}