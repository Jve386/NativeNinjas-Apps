package com.nativeninjas.vista.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.nativeninjas.data.model.QuoteModel
import com.nativeninjas.domain.GetQuotesUseCase
import com.nativeninjas.domain.GetRandomQuoteUseCase

class QuoteViewModel : ViewModel() {

    val quoteModel = MutableLiveData<QuoteModel>()
    val isLoading = MutableLiveData<Boolean>()

    private val getQuotesUseCase = GetQuotesUseCase()
    private val getRandomQuoteUseCase = GetRandomQuoteUseCase()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getQuotesUseCase()

            if (!result.isNullOrEmpty()) {
                quoteModel.postValue(result[0])
                isLoading.postValue(false)
            }
        }
    }

    fun randomQuote() {
        isLoading.postValue(true)
        val quote = getRandomQuoteUseCase()
        quote?.let {
            quoteModel.postValue(it)
        }
        isLoading.postValue(false)
    }
}