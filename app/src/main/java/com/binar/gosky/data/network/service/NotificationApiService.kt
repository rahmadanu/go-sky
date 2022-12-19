package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.notification.NotificationResponse
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotificationApiService {

    @GET(ApiEndPoints.GET_NOTIFICATION)
    suspend fun getNotification(): NotificationResponse

    @PUT(ApiEndPoints.PUT_NOTIFICATION_READ)
    suspend fun putNotificationRead(
        @Path("id") notificationId: Int
    )
}