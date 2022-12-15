package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.TransactionsRemoteDataSource
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionRequestBody
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionResponse
import javax.inject.Inject

interface TransactionsRepository {
    suspend fun postNewTransaction(accessToken: String, newTransactionRequestBody: NewTransactionRequestBody
    ): NewTransactionResponse
}

class TransactionsRepositoryImpl @Inject constructor(private val dataSource: TransactionsRemoteDataSource): TransactionsRepository {
    override suspend fun postNewTransaction(
        accessToken: String,
        newTransactionRequestBody: NewTransactionRequestBody
    ): NewTransactionResponse {
        return dataSource.postNewTransaction(accessToken, newTransactionRequestBody)
    }

}