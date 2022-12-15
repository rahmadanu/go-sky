package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionRequestBody
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionResponse
import com.binar.gosky.data.network.service.TransactionsApiService
import retrofit2.http.Body
import retrofit2.http.Header
import javax.inject.Inject

interface TransactionsRemoteDataSource {
    suspend fun postNewTransaction(accessToken: String, newTransactionRequestBody: NewTransactionRequestBody
    ): NewTransactionResponse
}

class TransactionsRemoteDataSourceImpl @Inject constructor(private val apiService: TransactionsApiService): TransactionsRemoteDataSource {
    override suspend fun postNewTransaction(
        accessToken: String,
        newTransactionRequestBody: NewTransactionRequestBody
    ): NewTransactionResponse {
        return apiService.postNewTransaction(accessToken, newTransactionRequestBody)
    }
}