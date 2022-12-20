package com.binar.gosky.presentation.ui.notification

import androidx.lifecycle.*
import com.binar.gosky.data.network.model.notification.NotificationResponse
import com.binar.gosky.data.repository.NotificationRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _notificationResponse = MutableLiveData<Resource<NotificationResponse>>()
    val notificationResponse: LiveData<Resource<NotificationResponse>> get() = _notificationResponse

    fun getNotification(accessToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = notificationRepository.getNotification(accessToken)
            viewModelScope.launch(Dispatchers.Main) {
                _notificationResponse.postValue(response)
            }
        }
    }

    fun getUserAccessToken(): LiveData<String> {
        return userRepository.getUserAccessToken().asLiveData()
    }
}