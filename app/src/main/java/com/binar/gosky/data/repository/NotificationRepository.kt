package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.NotificationRemoteDataSource
import com.binar.gosky.data.network.model.notification.NotificationResponse
import com.binar.gosky.util.proceed
import com.binar.gosky.wrapper.Resource
import javax.inject.Inject

interface NotificationRepository {
    suspend fun getNotification(): Resource<NotificationResponse>
    suspend fun putNotificationRead(notificationId: Int)
}

class NotificationRepositoryImpl @Inject constructor(private val dataSource: NotificationRemoteDataSource) :
    NotificationRepository {
    override suspend fun getNotification(): Resource<NotificationResponse> {
        return proceed {
            dataSource.getNotification()
        }
    }

    override suspend fun putNotificationRead(notificationId: Int) {
        dataSource.putNotificationRead(notificationId)
    }

}