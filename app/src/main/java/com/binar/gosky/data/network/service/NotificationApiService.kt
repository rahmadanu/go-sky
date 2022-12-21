package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.notification.NotificationResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotificationApiService {

    @GET(ApiEndPoints.GET_NOTIFICATION)
    suspend fun getNotification(
        @Header("Authorization") accessToken: String
    ): NotificationResponse

    @PUT(ApiEndPoints.PUT_NOTIFICATION_READ)
    suspend fun putNotificationRead(
        @Header("Authorization") accessToken: String,
        @Path("id") notificationId: Int
    )
}