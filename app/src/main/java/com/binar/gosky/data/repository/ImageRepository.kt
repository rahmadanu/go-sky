package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.ImageRemoteDataSource
import com.binar.gosky.data.network.model.image.ImageResponse
import com.binar.gosky.util.proceed
import com.binar.gosky.wrapper.Resource
import okhttp3.MultipartBody
import javax.inject.Inject

interface ImageRepository {
    suspend fun postImage(accessToken: String,imageType: String,imageBody: MultipartBody.Part): Resource<ImageResponse>
    suspend fun deleteImage(accessToken: String, imageType: String, imageId: String): Resource<ImageResponse>
}

class ImageRepositoryImpl @Inject constructor(private val dataSource: ImageRemoteDataSource): ImageRepository {
    override suspend fun postImage(
        accessToken: String,
        imageType: String,
        imageBody: MultipartBody.Part
    ): Resource<ImageResponse> {
        return proceed {
            dataSource.postImage(accessToken, imageType, imageBody)
        }
    }

    override suspend fun deleteImage(accessToken: String, imageType: String, imageId: String): Resource<ImageResponse> {
        return proceed {
            dataSource.deleteImage(accessToken, imageType, imageId)
        }
    }
}