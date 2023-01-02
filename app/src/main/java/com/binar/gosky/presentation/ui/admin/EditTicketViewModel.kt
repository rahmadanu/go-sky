package com.binar.gosky.presentation.ui.admin

import androidx.lifecycle.*
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

    private val _addTicketResponse = MutableLiveData<Resource<EditTicketResponse>>()
    val addTicketResponse: LiveData<Resource<EditTicketResponse>> get() = _addTicketResponse

    fun putTicketById(accessToken: String, id: Int, editTicket: EditTicketRequestBody) {
        _editTicketResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = ticketsRepository.putTicketById(accessToken, id, editTicket)
            viewModelScope.launch(Dispatchers.Main) {
                _editTicketResponse.postValue(response)
            }
        }
    }

    fun postTicket(accessToken: String, postTicketRequest: EditTicketRequestBody) {
        _addTicketResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = ticketsRepository.postTicket(accessToken, postTicketRequest)
            viewModelScope.launch(Dispatchers.Main) {
                _addTicketResponse.postValue(response)
            }
        }
    }

    fun getUserAccessToken(): LiveData<String> = userRepository.getUserAccessToken().asLiveData()
}
