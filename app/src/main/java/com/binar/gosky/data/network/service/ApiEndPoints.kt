package com.binar.gosky.data.network.service

object ApiEndPoints {
    //Tickets
    const val GET_TICKETS_ENDPOINT = "tickets"
    const val GET_TICKETS_BY_ID_ENDPOINT = "tickets/{id}"

    //Transactions
    const val GET_TRANSACTION_LIST_ENDPOINT = "transactions"
    const val GET_TRANSACTION_BY_ID_ENDPOINT = "transactions/{id}"
    const val POST_NEW_TRANSACTION = "transactions"
}