package com.nativeninjas.domain

import com.nativeninjas.data.model.QuoteModel
import com.nativeninjas.data.model.QuoteProvider
import com.nativeninjas.data.QuoteRepository

class GetRandomQuoteUseCase {

    operator fun invoke():QuoteModel?{
        val quotes = QuoteProvider.quotes
        if(!quotes.isNullOrEmpty()){
            val randomNumber = (quotes.indices).random()
            return quotes[randomNumber]
        }
        return null
    }
}