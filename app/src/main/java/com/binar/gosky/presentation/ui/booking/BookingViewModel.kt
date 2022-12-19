package com.binar.gosky.presentation.ui.booking

import androidx.lifecycle.*
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionRequestBody
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionResponse
import com.binar.gosky.data.network.model.users.data.UserByIdResponse
import com.binar.gosky.data.repository.TicketsRepository
import com.binar.gosky.data.repository.TransactionsRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val ticketsRepository: TicketsRepository,
    private val transactionsRepository: TransactionsRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _newTransactionResponse = MutableLiveData<Resource<NewTransactionResponse>>()
    val newTransactionResponse: LiveData<Resource<NewTransactionResponse>> = _newTransactionResponse

    private val _userByIdResponse = MutableLiveData<Resource<UserByIdResponse>>()
    val userByIdResponse: LiveData<Resource<UserByIdResponse>> get() = _userByIdResponse

    fun postNewTransaction(accessToken: String, newTransactionRequestBody: NewTransactionRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            _newTransactionResponse.postValue(Resource.Loading())
            val response = transactionsRepository.postNewTransaction(accessToken, newTransactionRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _newTransactionResponse.postValue(response)
            }
        }
    }

    fun getUserAccessToken(): LiveData<String> {
        return userRepository.getUserAccessToken().asLiveData()
    }

    fun getUserById(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository.getUserById(userId)
            viewModelScope.launch(Dispatchers.Main) {
                _userByIdResponse.postValue(response)
            }
        }
    }

}

