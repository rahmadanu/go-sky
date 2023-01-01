package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.image.ImageResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ImageApiService {

    @Multipart
    @POST(ApiEndPoints.POST_IMAGE)
    suspend fun postImage(
        @Header("Authorization") accessToken: String,
        @Query("type") imageType: String,
        @Part imageBody: MultipartBody.Part
    ): ImageResponse

    @DELETE(ApiEndPoints.DELETE_IMAGE)
    suspend fun deleteImage(
        @Header("Authorization") accessToken: String,
        @Query("type") imageType: String,
        @Query("imageId") imageId: String
    ): ImageResponse
}