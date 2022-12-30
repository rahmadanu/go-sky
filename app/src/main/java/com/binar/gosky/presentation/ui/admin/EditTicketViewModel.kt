package com.binar.gosky.presentation.ui.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.binar.gosky.data.network.model.tickets.EditTicketRequestBody
import com.binar.gosky.data.network.model.tickets.EditTicketResponse
import com.binar.gosky.data.repository.TicketsRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTicketViewModel @Inject constructor(private val ticketsRepository: TicketsRepository, private val userRepository: UserRepository): ViewModel() {

    private val _editTicketResponse = MutableLiveData<Resource<EditTicketResponse>>()
    val editTicketResponse: LiveData<Resource<EditTicketResponse>> get() = _editTicketResponse

    fun putTicketById(accessToken: String, id: Int, editTicket: EditTicketRequestBody) {
        _editTicketResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = ticketsRepository.putTicketById(accessToken, id, editTicket)
            viewModelScope.launch(Dispatchers.Main) {
                _editTicketResponse.postValue(response)
            }
        }
    }

    //fun deleteTicketById(accessToken: String, id: Int)

    fun getUserAccessToken(): LiveData<String> = userRepository.getUserAccessToken().asLiveData()
}