package com.binar.gosky.presentation.ui.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.gosky.R
import com.binar.gosky.data.network.model.image.ImageData
import com.binar.gosky.data.network.model.users.EditUserRequestBody
import com.binar.gosky.databinding.FragmentEditProfileBinding
import com.binar.gosky.presentation.ui.auth.ValidateEmailBottomSheet
import com.binar.gosky.util.ImageUtil
import com.binar.gosky.wrapper.Resource
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val editProfileArgs: EditProfileFragmentArgs by navArgs()

    private val viewModel: EditProfileViewModel by viewModels()

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            val imageFile = ImageUtil.getImageMultipartBody(requireContext(), result)
            if (imageFile != null) {
                viewModel.postImage("Bearer ${editProfileArgs.accessToken}",
                    ImageUtil.IMAGE_TYPE_PROFILE, imageFile)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setOnClickListener()
        observeData()
    }

    private fun observeData() {
        viewModel.imageResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    Log.d("imageIdSuccess", it.payload?.data?.imageId.toString())
                    editProfileArgs.userData.imageId = it.payload?.data?.imageId
                    editProfileArgs.userData.imageUrl = it.payload?.data?.imageUrl
                    setProfileImage(it.payload?.data)
                }
                else -> {}
            }
            Log.d("imageresponse", it.payload?.data.toString())
        }
    }

    private fun setProfileImage(image: ImageData?) {
        Glide.with(requireContext())
            .load(image?.imageUrl)
            .placeholder(R.drawable.ic_placeholder_image)
            .into(binding.ivImage)
    }

    private fun parseFormIntoEntity(): EditUserRequestBody {
        Log.d("imageIdAfterSave", editProfileArgs.userData.imageId.toString())
        return EditUserRequestBody(
            name = binding.etName.text.toString(),
            address = binding.etAddress.text.toString(),
            phone = binding.etPhoneNo.text.toString(),
            imageId = editProfileArgs.userData.imageId,
            imageUrl = editProfileArgs.userData.imageUrl
        )
    }

    private fun initView() {
        binding.apply {
            with(editProfileArgs.userData) {
                etName.setText(name)
                etAddress.setText(address)
                etPhoneNo.setText(phone)
                etEmail.setText(editProfileArgs.email)
                Glide.with(requireContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_placeholder_image)
                    .into(ivImage)
                if (editProfileArgs.userData.imageId != null) {
                    tvAddImage.isVisible = false
                    tvDeleteImage.isVisible = true
                } else {
                    tvAddImage.isVisible = true
                    tvDeleteImage.isVisible = false
                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.ivImage.setOnClickListener {
            getImageFromGallery()
        }
        binding.tvAddImage.setOnClickListener {
            getImageFromGallery()
        }
        binding.tvDeleteImage.setOnClickListener {
            editProfileArgs.userData.imageId?.let { it1 ->
                viewModel.deleteImage("Bearer ${editProfileArgs.accessToken}",
                    ImageUtil.IMAGE_TYPE_PROFILE,
                    it1
                )
                editProfileArgs.userData.imageId = ""
                editProfileArgs.userData.imageUrl = ""
                Log.d("imageIdAfterDelete", editProfileArgs.userData.imageId.toString())
            }
        }
        binding.btnSave.setOnClickListener {
            if (emailChanged()) {
                showValidateEmailDialog()
                viewModel.putUserData("Bearer ${editProfileArgs.accessToken}", parseFormIntoEntity())
            } else {
                viewModel.putUserData("Bearer ${editProfileArgs.accessToken}", parseFormIntoEntity())
                findNavController().navigate(R.id.action_editProfileFragment_to_accountFragment)
            }
        }
    }

    private fun getImageFromGallery() {
        activity?.intent?.type = "image/*"
        galleryResult.launch("image/*")
    }

    private fun showValidateEmailDialog(
        isCancelable: Boolean = true,

        ) {
        val currentDialog = parentFragmentManager.findFragmentByTag(ValidateEmailBottomSheet::class.java.simpleName)
        if (currentDialog == null) {
            val email = binding.etEmail.text.toString().trim()
            ValidateEmailBottomSheet(email = email, isRegistered = true, accessToken = editProfileArgs.accessToken).apply {

                this.isCancelable = isCancelable
            }.show(parentFragmentManager, ValidateEmailBottomSheet::class.java.simpleName)
        }
    }

    private fun emailChanged(): Boolean {
        if (editProfileArgs.email != binding.etEmail.text.toString()) {
            return true
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}