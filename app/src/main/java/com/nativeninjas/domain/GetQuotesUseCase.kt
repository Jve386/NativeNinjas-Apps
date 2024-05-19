package com.nativeninjas.domain

import com.nativeninjas.data.QuoteRepository
import com.nativeninjas.data.model.QuoteModel
class GetQuotesUseCase {
    private val repository = QuoteRepository()
    suspend operator fun invoke() = repository.getAllQuotes()
}