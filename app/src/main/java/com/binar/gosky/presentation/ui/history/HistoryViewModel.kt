package com.binar.gosky.presentation.ui.history

import androidx.lifecycle.*
import com.binar.gosky.data.network.model.transactions.byid.TransactionByIdResponse
import com.binar.gosky.data.network.model.transactions.list.TransactionListResponse
import com.binar.gosky.data.repository.TransactionsRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val userRepository: UserRepository
): ViewModel(){

    private var _transactionListResponse = MutableLiveData<Resource<TransactionListResponse>>()
    val transactionListResponse: LiveData<Resource<TransactionListResponse>> get() = _transactionListResponse

    private var _transactionByIdResponse = MutableLiveData<Resource<TransactionByIdResponse>>()
    val transactionByIdResponse: LiveData<Resource<TransactionByIdResponse>> get() = _transactionByIdResponse

    fun getTransactionList(accessToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = transactionsRepository.getTransactionList(accessToken)
            viewModelScope.launch(Dispatchers.Main) {
                _transactionListResponse.postValue(response)
            }
        }
    }

    fun getTransactionById(accessToken: String, transactionId: Int?) {
        _transactionByIdResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = transactionsRepository.getTransactionById(accessToken, transactionId)
            viewModelScope.launch(Dispatchers.Main) {
                _transactionByIdResponse.postValue(response)
            }
        }
    }

    fun getUserAccessToken(): LiveData<String> {
        return userRepository.getUserAccessToken().asLiveData()
    }
}