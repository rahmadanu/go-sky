package com.binar.gosky.data.network.datasource

import com.binar.gosky.data.network.model.transactions.earnings.EarningsResponse
import com.binar.gosky.data.network.model.transactions.list.TransactionListResponse
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionRequestBody
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionResponse
import com.binar.gosky.data.network.service.TransactionsApiService
import javax.inject.Inject

interface TransactionsRemoteDataSource {
    suspend fun postNewTransaction(accessToken: String, newTransactionRequestBody: NewTransactionRequestBody
    ): NewTransactionResponse
    suspend fun getTransactionList(accessToken: String): TransactionListResponse
    suspend fun getEarnings(accessToken: String): EarningsResponse
}

class TransactionsRemoteDataSourceImpl @Inject constructor(private val apiService: TransactionsApiService): TransactionsRemoteDataSource {
    override suspend fun postNewTransaction(
        accessToken: String,
        newTransactionRequestBody: NewTransactionRequestBody
    ): NewTransactionResponse {
        return apiService.postNewTransaction(accessToken, newTransactionRequestBody)
    }

    override suspend fun getTransactionList(accessToken: String): TransactionListResponse {
        return apiService.getTransactionList(accessToken)
    }

    override suspend fun getEarnings(accessToken: String): EarningsResponse {
        return apiService.getEarnings(accessToken)
    }
}
