package com.binar.gosky.data.network.service

import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionRequestBody
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionResponse
import com.binar.gosky.data.network.model.transactions.byid.TransactionByIdResponse
import com.binar.gosky.data.network.model.transactions.earnings.EarningsResponse
import com.binar.gosky.data.network.model.transactions.list.TransactionListResponse
import retrofit2.http.*

interface TransactionsApiService {

    @GET(ApiEndPoints.GET_TRANSACTION_LIST_ENDPOINT)
    suspend fun getTransactionList(
        @Header("Authorization") accessToken: String
    ): TransactionListResponse

    @GET(ApiEndPoints.GET_TRANSACTION_BY_ID_ENDPOINT)
    suspend fun getTransactionById(
        @Header("Authorization") accessToken: String,
        @Path("id") transactionId: Int?
    ): TransactionByIdResponse

    @POST(ApiEndPoints.POST_NEW_TRANSACTION)
    suspend fun postNewTransaction(
        @Header("Authorization") accessToken: String,
        @Body newTransactionRequestBody: NewTransactionRequestBody
    ): NewTransactionResponse

    @GET(ApiEndPoints.GET_EARNINGS)
    suspend fun getEarnings(
        @Header("Authorization") accessToken: String
    ): EarningsResponse
}