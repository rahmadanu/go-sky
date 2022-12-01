package com.binar.gosky.presentation.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.gosky.data.network.model.tickets.TicketsItem
import com.binar.gosky.data.repository.TicketsRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(private val ticketsRepository: TicketsRepository): ViewModel() {

    private val _ticketsResult = MutableLiveData< Resource<List<TicketsItem>>>()
    val ticketsResult: LiveData<Resource<List<TicketsItem>>> get() = _ticketsResult

    init {
        getTickets()
    }

    private fun getTickets() {
        viewModelScope.launch(Dispatchers.IO) {
            _ticketsResult.postValue(Resource.Loading())

            val ticketsItem = ticketsRepository.getTickets().payload?.data
            Log.d("testing", ticketsRepository.getTickets().payload?.status.toString())
            viewModelScope.launch(Dispatchers.Main) {
                _ticketsResult.postValue(Resource.Success(ticketsItem))
            }
        }
    }
}