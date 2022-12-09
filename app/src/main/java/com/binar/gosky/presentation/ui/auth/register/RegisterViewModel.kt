package com.binar.gosky.presentation.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.gosky.data.network.model.auth.otp.OtpResponse
import com.binar.gosky.data.repository.AuthRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private var _otpResponse = MutableLiveData<Resource<OtpResponse>>()
    val otpResponse: LiveData<Resource<OtpResponse>> get() = _otpResponse

    fun getOtp(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _otpResponse.postValue(repository.getOtp(email))
        }
    }

}