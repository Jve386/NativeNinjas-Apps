package com.nativeninjas.data

import com.nativeninjas.data.model.QuoteModel
import com.nativeninjas.data.model.QuoteProvider
import com.nativeninjas.data.network.QuoteService
class QuoteRepository {

    private val api = QuoteService()

    suspend fun getAllQuotes(): List<QuoteModel> {
        val response = api.getQuotes()
        QuoteProvider.quotes = response
        return response
    }
}