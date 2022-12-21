package com.binar.gosky.util

import com.binar.gosky.wrapper.Resource

suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(coroutines.invoke())
    } catch (e: Exception) {
        Resource.Error(e, e.message)
    }/* catch (httpE: HttpException) {
            val response = httpE.response()?.errorBody()?.string()
            Resource.Error(httpE, httpE.response()?.message())
        }*/
}