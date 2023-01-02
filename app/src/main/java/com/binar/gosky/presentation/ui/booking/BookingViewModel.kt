package com.binar.gosky.presentation.ui.booking

import androidx.lifecycle.*
import com.binar.gosky.data.network.model.tickets.TicketsItem
import com.binar.gosky.data.network.model.tickets.WishlistResponse
import com.binar.gosky.data.network.model.transactions.byid.TransactionByIdResponse
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

    private var _transactionByIdResponse = MutableLiveData<Resource<TransactionByIdResponse>>()
    val transactionByIdResponse: LiveData<Resource<TransactionByIdResponse>> get() = _transactionByIdResponse

    private val _userByIdResponse = MutableLiveData<Resource<UserByIdResponse>>()
    val userByIdResponse: LiveData<Resource<UserByIdResponse>> get() = _userByIdResponse

    private val _wishlistResponse = MutableLiveData<Resource<WishlistResponse>>()
    val wishlistResponse: LiveData<Resource<WishlistResponse>> get() = _wishlistResponse

    private val ticketItem = MutableLiveData<TicketsItem>()

    fun postNewTransaction(accessToken: String, newTransactionRequestBody: NewTransactionRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            _newTransactionResponse.postValue(Resource.Loading())
            val response = transactionsRepository.postNewTransaction(accessToken, newTransactionRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _newTransactionResponse.postValue(response)
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

    fun getUserById(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository.getUserById(userId)
            viewModelScope.launch(Dispatchers.Main) {
                _userByIdResponse.postValue(response)
            }
        }
    }

    fun setTicketItem(item: TicketsItem) {
        ticketItem.value = item
    }

    val wishlistStatus = ticketItem.switchMap {
        it.id?.let { id -> ticketsRepository.isTicketWishlisted(id) }
    }

    fun changeWishlist(accessToken: String, item: TicketsItem) {
        _wishlistResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            if (wishlistStatus.value as Boolean) {
                ticketsRepository.deleteTicketFromLocalWishlist(item)
                item.id?.let {
                    _wishlistResponse.postValue(ticketsRepository.deleteTicketFromRemoteWishlist(accessToken, it) )
                }
            } else {
                ticketsRepository.addTicketToLocalWishlist(item)
                item.id?.let {
                    _wishlistResponse.postValue(ticketsRepository.postTicketToRemoteWishlist(accessToken, it))
                }
            }
        }
    }
}

