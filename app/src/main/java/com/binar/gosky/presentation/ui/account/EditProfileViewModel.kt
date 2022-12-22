package com.binar.gosky.presentation.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.gosky.data.network.model.image.ImageResponse
import com.binar.gosky.data.network.model.users.EditEmailUserRequestBody
import com.binar.gosky.data.network.model.users.EditEmailUserResponse
import com.binar.gosky.data.network.model.users.EditUserRequestBody
import com.binar.gosky.data.repository.ImageRepository
import com.binar.gosky.data.repository.UserRepository
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val imageRepository: ImageRepository
): ViewModel() {

    private val _editEmailUserResponse = MutableLiveData<Resource<EditEmailUserResponse>>()
    val editEmailUserResponse: LiveData<Resource<EditEmailUserResponse>> get() = _editEmailUserResponse

    private val _imageResponse = MutableLiveData<Resource<ImageResponse>>()
    val imageResponse: LiveData<Resource<ImageResponse>> get() = _imageResponse

    fun putUserData(accessToken: String, editUserRequestBody: EditUserRequestBody) {
        viewModelScope.launch {
            userRepository.putUserData(accessToken, editUserRequestBody)
        }
    }

    fun putUserEmail(accessToken: String, editEmailUserRequestBody: EditEmailUserRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val editEmailUserResponse = userRepository.putUserEmail(accessToken, editEmailUserRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _editEmailUserResponse.postValue(editEmailUserResponse)
            }
        }
    }

    fun postImage(accessToken: String, imageType: String, imageBody: MultipartBody.Part) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = imageRepository.postImage(accessToken, imageType, imageBody)
            viewModelScope.launch(Dispatchers.Main) {
                _imageResponse.postValue(response)
            }
        }
    }

    fun deleteImage(accessToken: String, imageId: String) {
        viewModelScope.launch {
            imageRepository.deleteImage(accessToken, imageId)
        }
    }

}