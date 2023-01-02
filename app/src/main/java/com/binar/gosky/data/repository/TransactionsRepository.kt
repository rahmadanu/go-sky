package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.TransactionsRemoteDataSource
import com.binar.gosky.data.network.model.transactions.earnings.EarningsResponse
import com.binar.gosky.data.network.model.transactions.list.TransactionListResponse
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionRequestBody
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionResponse
import com.binar.gosky.util.proceed
import com.binar.gosky.wrapper.Resource
import javax.inject.Inject

interface TransactionsRepository {
    suspend fun postNewTransaction(accessToken: String, newTransactionRequestBody: NewTransactionRequestBody
    ): Resource<NewTransactionResponse>
    suspend fun getTransactionList(accessToken: String): Resource<TransactionListResponse>
    suspend fun getEarnings(accessToken: String): Resource<EarningsResponse>
}

class TransactionsRepositoryImpl @Inject constructor(private val dataSource: TransactionsRemoteDataSource): TransactionsRepository {
    override suspend fun postNewTransaction(
        accessToken: String,
        newTransactionRequestBody: NewTransactionRequestBody
    ): Resource<NewTransactionResponse> {
        return proceed {
            dataSource.postNewTransaction(accessToken, newTransactionRequestBody)
        }
    }

    override suspend fun getTransactionList(accessToken: String): Resource<TransactionListResponse> {
        return proceed {
            dataSource.getTransactionList(accessToken)
        }
    }

    override suspend fun getEarnings(accessToken: String): Resource<EarningsResponse> {
        return proceed {
            dataSource.getEarnings(accessToken)
        }
    }
}