package com.binar.gosky.presentation.ui.account

import androidx.lifecycle.*
import com.binar.gosky.data.network.model.auth.user.CurrentUserResponse
import com.binar.gosky.data.network.model.users.UserRequestBody
import com.binar.gosky.data.repository.AuthRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val authRepository: AuthRepository, private val userRepository: UserRepository): ViewModel() {

    private val _currentUserResponse = MutableLiveData<Resource<CurrentUserResponse>>()
    val currentUserResponse: LiveData<Resource<CurrentUserResponse>> = _currentUserResponse

    fun getCurrentUser(accessToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = authRepository.getCurrentUser(accessToken)
            viewModelScope.launch(Dispatchers.Main) {
                _currentUserResponse.postValue(currentUser)
            }
        }
    }

    fun getUserAccessToken(): LiveData<String> {
        return userRepository.getUserAccessToken().asLiveData()
    }

    fun setUserAccessToken(accessToken: String) {
        viewModelScope.launch {
            userRepository.setUserAccessToken(accessToken)
        }
    }

    fun setUserLogin(isLogin: Boolean) {
        viewModelScope.launch {
            userRepository.setUserLogin(isLogin)
        }
    }

    fun putUserData(accessToken: String, userRequestBody: UserRequestBody) {
        viewModelScope.launch {
            userRepository.putUserData(accessToken, userRequestBody)
        }
    }

}