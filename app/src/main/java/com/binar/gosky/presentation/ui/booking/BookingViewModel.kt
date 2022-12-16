package com.binar.gosky.presentation.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionRequestBody
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionResponse
import com.binar.gosky.data.repository.TicketsRepository
import com.binar.gosky.data.repository.TransactionsRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(private val ticketsRepository: TicketsRepository, private val transactionsRepository: TransactionsRepository): ViewModel() {

    private val _newTransactionResponse = MutableLiveData<Resource<NewTransactionResponse>>()
    val newTransactionResponse: LiveData<Resource<NewTransactionResponse>> = _newTransactionResponse

    fun postNewTransaction(accessToken: String, newTransactionRequestBody: NewTransactionRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            _newTransactionResponse.postValue(Resource.Loading())
            val response = transactionsRepository.postNewTransaction(accessToken, newTransactionRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _newTransactionResponse.postValue(response)
            }
        }
    }
}

