package com.binar.gosky.data.network.service

object ApiEndPoints {
    // Tickets
    const val GET_TICKETS_ENDPOINT = "tickets"
    const val GET_TICKETS_BY_ID_ENDPOINT = "tickets/{id}"
    const val POST_TICKET = "tickets"
    const val PUT_TICKETS_BY_ID = "tickets/{id}"
    const val DELETE_TICKETS_BY_ID = "tickets/{id}"
    const val GET_WISHLIST = "wishlist"
    const val POST_TICKET_TO_WISHLIST = "tickets/{id}/wishlist"
    const val DELETE_TICKET_FROM_WISHLIST = "tickets/{id}/wishlist"

    //Transactions
    const val GET_TRANSACTION_LIST_ENDPOINT = "transactions"
    const val GET_TRANSACTION_BY_ID_ENDPOINT = "transactions/{id}"
    const val POST_NEW_TRANSACTION = "transactions"
    const val GET_EARNINGS = "earnings"

    // Auth
    const val GET_OTP = "auth/otp"
    const val GET_WHO_AM_I = "auth/whoami"
    const val POST_REGISTER = "auth/register"
    const val POST_LOGIN = "auth/login"
    const val PUT_PASSWORD_FORGOT = "auth/password"
    const val PUT_PASSWORD_RESET = "users/password"

    // Users
    const val PUT_USER_DATA = "users"
    const val PUT_USER_EMAIL = "users/email"
    const val GET_USER_BY_ID = "users/{id}"

    // Notification
    const val GET_NOTIFICATION = "notifications"
    const val PUT_NOTIFICATION_READ = "notifications/{id}/read"

    // Image
    const val POST_IMAGE = "images"
    const val DELETE_IMAGE = "images"
}