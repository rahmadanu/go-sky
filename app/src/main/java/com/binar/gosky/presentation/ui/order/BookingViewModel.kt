package com.binar.gosky.presentation.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.repository.TicketsRepository
import com.binar.gosky.data.repository.TransactionsRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(private val ticketsRepository: TicketsRepository, private val transactionsRepository: TransactionsRepository): ViewModel() {

    private val _getTicketByIdResponse = MutableLiveData<Resource<Tickets>>()
    val getTicketById: LiveData<Resource<Tickets>> = _getTicketByIdResponse

    fun getTicketById(accessToken: String, id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val ticket = ticketsRepository.getTicketById(accessToken, id)
            viewModelScope.launch(Dispatchers.Main) {
                _getTicketByIdResponse.postValue(ticket)
            }
        }
    }
}

