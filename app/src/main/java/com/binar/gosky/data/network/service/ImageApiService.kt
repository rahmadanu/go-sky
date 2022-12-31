package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.image.ImageResponse
import okhttp3.MultipartBody
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

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