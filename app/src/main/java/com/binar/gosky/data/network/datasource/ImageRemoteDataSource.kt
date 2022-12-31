package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.image.ImageResponse
import com.binar.gosky.data.network.service.ApiEndPoints
import com.binar.gosky.data.network.service.ImageApiService
import okhttp3.MultipartBody
import retrofit2.http.*
import javax.inject.Inject

interface ImageRemoteDataSource {
    suspend fun postImage(accessToken: String,imageType: String,imageBody: MultipartBody.Part): ImageResponse
    suspend fun deleteImage(accessToken: String, imageType: String, imageId: String): ImageResponse
}

class ImageRemoteDataSourceImpl @Inject constructor(private val apiService: ImageApiService): ImageRemoteDataSource {
    override suspend fun postImage(
        accessToken: String,
        imageType: String,
        imageBody: MultipartBody.Part
    ): ImageResponse {
        return apiService.postImage(accessToken, imageType, imageBody)
    }

    override suspend fun deleteImage(accessToken: String, imageType: String, imageId: String): ImageResponse {
        return apiService.deleteImage(accessToken, imageType, imageId)
    }
}