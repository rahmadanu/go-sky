package com.binar.gosky.presentation.ui.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.binar.gosky.data.network.model.image.ImageResponse
import com.binar.gosky.data.network.model.tickets.EditTicketRequestBody
import com.binar.gosky.data.network.model.tickets.EditTicketResponse
import com.binar.gosky.data.repository.ImageRepository
import com.binar.gosky.data.repository.TicketsRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class EditTicketViewModel @Inject constructor(
    private val ticketsRepository: TicketsRepository,
    private val userRepository: UserRepository,
    private val imageRepository: ImageRepository
): ViewModel() {

    private val _editTicketResponse = MutableLiveData<Resource<EditTicketResponse>>()
    val editTicketResponse: LiveData<Resource<EditTicketResponse>> get() = _editTicketResponse

    private val _addTicketResponse = MutableLiveData<Resource<EditTicketResponse>>()
    val addTicketResponse: LiveData<Resource<EditTicketResponse>> get() = _addTicketResponse

    private val _imageResponse = MutableLiveData<Resource<ImageResponse>>()
    val imageResponse: LiveData<Resource<ImageResponse>> get() = _imageResponse

    private val _deleteImageResponse = MutableLiveData<Resource<ImageResponse>>()
    val deleteImageResponse: LiveData<Resource<ImageResponse>> get() = _deleteImageResponse

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


    fun postImage(accessToken: String, imageType: String, imageBody: MultipartBody.Part) {
        _imageResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = imageRepository.postImage(accessToken, imageType, imageBody)
            viewModelScope.launch(Dispatchers.Main) {
                _imageResponse.postValue(response)
            }
        }
    }

    fun deleteImage(accessToken: String, imageType: String, imageId: String) {
        _deleteImageResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = imageRepository.deleteImage(accessToken, imageType, imageId)
            viewModelScope.launch(Dispatchers.Main) {
                _deleteImageResponse.postValue(response)
            }
        }
    }
}