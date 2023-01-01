package com.binar.gosky.presentation.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.binar.gosky.data.local.model.TicketsItemWishlist
import com.binar.gosky.data.network.model.tickets.EditTicketResponse
import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.repository.TicketsRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(private val ticketsRepository: TicketsRepository, private val userRepository: UserRepository): ViewModel() {

    private val _getWishlistResponse = MutableLiveData<Resource<Tickets>>()
    val getWishlistResponse: LiveData<Resource<Tickets>> get() = _getWishlistResponse

    private val _deleteTicketResponse = MutableLiveData<Resource<EditTicketResponse>>()
    val deleteTicketResponse: LiveData<Resource<EditTicketResponse>> get() = _deleteTicketResponse

    fun getWishlist(accessToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = ticketsRepository.getWishlist(accessToken)
            response.payload?.data?.forEach {
                ticketsRepository.addTicketToLocalWishlist(it)
            }
            viewModelScope.launch(Dispatchers.Main) {
                _getWishlistResponse.postValue(response)
            }
        }
    }

    fun getUserAccessToken(): LiveData<String> {
        return userRepository.getUserAccessToken().asLiveData()
    }

    fun getWishlistTickets(): LiveData<List<TicketsItemWishlist>> {
        return ticketsRepository.getWishlistTickets()
    }

    fun deleteTicketById(accessToken: String, id: Int) {
        _deleteTicketResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            _deleteTicketResponse.postValue(ticketsRepository.deleteTicketById(accessToken, id))
        }
    }

}