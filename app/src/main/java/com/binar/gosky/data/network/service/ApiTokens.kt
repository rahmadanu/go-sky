package com.binar.gosky.data.network.service

object ApiTokens {
    var API_ACCESS_TOKEN = ""

    fun setAccessToken(token: String) {
        this.API_ACCESS_TOKEN = token
    }
}