package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.notification.NotificationResponse
import com.binar.gosky.data.network.service.ApiEndPoints
import com.binar.gosky.data.network.service.NotificationApiService
import retrofit2.http.PUT
import retrofit2.http.Path
import javax.inject.Inject

interface NotificationRemoteDataSource {
    suspend fun getNotification(): NotificationResponse
    suspend fun putNotificationRead(notificationId: Int)
}

class NotificationRemoteDataSourceImpl @Inject constructor(private val apiService: NotificationApiService) :
    NotificationRemoteDataSource {
    override suspend fun getNotification(): NotificationResponse {
        return apiService.getNotification()
    }

    override suspend fun putNotificationRead(notificationId: Int) {
        apiService.putNotificationRead(notificationId)
    }

}