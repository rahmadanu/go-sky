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
) : ViewModel() {
    private val _notificationResponse = MutableLiveData<Resource<NotificationResponse>>()
    val notificationResponse: LiveData<Resource<NotificationResponse>> get() = _notificationResponse

    private val _unreadNotificationCount = MutableLiveData<Int?>()
    val unreadNotificationCount: LiveData<Int?> get() = _unreadNotificationCount

    fun getNotification(accessToken: String) {
        _notificationResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val notificationResponse = notificationRepository.getNotification(accessToken)
            val unreadCount = notificationResponse.payload?.meta?.unreadCount
            viewModelScope.launch(Dispatchers.Main) {
                _notificationResponse.postValue(notificationResponse)
                _unreadNotificationCount.postValue(unreadCount)
            }
        }
    }

    fun putNotificationRead(accessToken: String, notificationId: Int) {
        viewModelScope.launch {
            notificationRepository.putNotificationRead(accessToken, notificationId)
        }
    }

    fun getUserAccessToken(): LiveData<String> {
        return userRepository.getUserAccessToken().asLiveData()
    }
}
