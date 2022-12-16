package com.binar.gosky.presentation.ui.account

import androidx.lifecycle.*
import com.binar.gosky.data.network.model.auth.user.CurrentUserResponse
import com.binar.gosky.data.network.model.users.EditEmailUserRequestBody
import com.binar.gosky.data.network.model.users.EditEmailUserResponse
import com.binar.gosky.data.network.model.users.EditUserRequestBody
import com.binar.gosky.data.repository.AuthRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val authRepository: AuthRepository, private val userRepository: UserRepository): ViewModel() {

    private val _currentUserResponse = MutableLiveData<Resource<CurrentUserResponse>>()
    val currentUserResponse: LiveData<Resource<CurrentUserResponse>> get() = _currentUserResponse

    private val _editEmailUserResponse = MutableLiveData<Resource<EditEmailUserResponse>>()
    val editEmailUserResponse: LiveData<Resource<EditEmailUserResponse>> get() = _editEmailUserResponse

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

    fun putUserData(accessToken: String, editUserRequestBody: EditUserRequestBody) {
        viewModelScope.launch {
            userRepository.putUserData(accessToken, editUserRequestBody)
        }
    }

    fun putUserEmail(accessToken: String, editEmailUserRequestBody: EditEmailUserRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val editEmailUserResponse = userRepository.putUserEmail(accessToken, editEmailUserRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _editEmailUserResponse.postValue(editEmailUserResponse)
            }
        }
    }

}