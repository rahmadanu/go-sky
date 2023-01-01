package com.binar.gosky.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.gosky.data.network.model.transactions.earnings.EarningsResponse
import com.binar.gosky.data.repository.TransactionsRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val ticketRepository: TransactionsRepository): ViewModel() {

    private val _earningsResponse = MutableLiveData<Resource<EarningsResponse>>()
    val earningsResponse: LiveData<Resource<EarningsResponse>> get() = _earningsResponse

    fun getEarnings(accessToken: String){
        _earningsResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = ticketRepository.getEarnings(accessToken)
            viewModelScope.launch(Dispatchers.Main) {
                _earningsResponse.postValue(response)
            }
        }
    }
}