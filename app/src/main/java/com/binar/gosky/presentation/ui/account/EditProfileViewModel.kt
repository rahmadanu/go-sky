package com.binar.gosky.presentation.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.gosky.data.network.model.image.ImageResponse
import com.binar.gosky.data.network.model.users.edit.EditEmailUserRequestBody
import com.binar.gosky.data.network.model.users.edit.EditUserRequestBody
import com.binar.gosky.data.network.model.users.edit.EditUserResponse
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

    private val _editUserEmailResponse = MutableLiveData<Resource<EditUserResponse>>()
    val editUserEmailResponse: LiveData<Resource<EditUserResponse>> get() = _editUserEmailResponse

    private val _editUserResponse = MutableLiveData<Resource<EditUserResponse>>()
    val editUserResponse: LiveData<Resource<EditUserResponse>> get() = _editUserResponse

    private val _imageResponse = MutableLiveData<Resource<ImageResponse>>()
    val imageResponse: LiveData<Resource<ImageResponse>> get() = _imageResponse

    private val _deleteImageResponse = MutableLiveData<Resource<ImageResponse>>()
    val deleteImageResponse: LiveData<Resource<ImageResponse>> get() = _deleteImageResponse

    fun putUserData(accessToken: String, editUserRequestBody: EditUserRequestBody) {
        _editUserResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository.putUserData(accessToken, editUserRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _editUserResponse.postValue(response)
            }
        }
    }

    fun putUserEmail(accessToken: String, editEmailUserRequestBody: EditEmailUserRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val editEmailUserResponse = userRepository.putUserEmail(accessToken, editEmailUserRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _editUserEmailResponse.postValue(editEmailUserResponse)
            }
        }
    }

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