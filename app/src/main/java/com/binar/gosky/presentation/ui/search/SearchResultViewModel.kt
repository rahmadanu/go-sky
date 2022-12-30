package com.binar.gosky.presentation.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.binar.gosky.data.network.model.tickets.TicketsItem
import com.binar.gosky.data.repository.TicketsRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val ticketsRepository: TicketsRepository,
    private val userRepository: UserRepository
) :
    ViewModel() {

    private val _ticketsResult = MutableLiveData<Resource<List<TicketsItem>>>()
    val ticketsResult: LiveData<Resource<List<TicketsItem>>> get() = _ticketsResult

    fun getTickets(
        category: String,
        from: String,
        to: String,
        departureTime: String,
        returnTime: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _ticketsResult.postValue(Resource.Loading())

            val ticketsItem = ticketsRepository.getTickets(
                category,
                from,
                to,
                departureTime,
                returnTime
            ).payload?.data

            Log.d(
                "testing",
                ticketsRepository.getTickets(
                    category,
                    from,
                    to,
                    departureTime,
                    returnTime
                ).payload?.data.toString()
            )
            viewModelScope.launch(Dispatchers.Main) {
                val data = if (ticketsItem.isNullOrEmpty()) Resource.Empty() else Resource.Success(
                    ticketsItem
                )
                _ticketsResult.postValue(data)
            }
        }
    }

    fun checkIfUserAdmin(): LiveData<String> {
        return userRepository.getUserRole().asLiveData()
    }
}