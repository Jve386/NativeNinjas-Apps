package com.nativeninjas.data.network

import com.nativeninjas.data.model.QuoteModel

import retrofit2.Response
import retrofit2.http.GET

interface QuoteApiClient {
    @GET("/.json")
    suspend fun getAllQuotes(): Response<List<QuoteModel>>
}