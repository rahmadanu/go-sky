package com.binar.gosky.presentation.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.gosky.R
import com.binar.gosky.data.network.model.users.EditUserRequestBody
import com.binar.gosky.databinding.FragmentEditProfileBinding
import com.binar.gosky.presentation.ui.auth.ValidateEmailBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val editProfileArgs: EditProfileFragmentArgs by navArgs()

    private val viewModel: AccountViewModel by viewModels()

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
    }

    private fun parseFormIntoEntity(): EditUserRequestBody {
        return EditUserRequestBody(
            name = binding.etName.text.toString(),
            address = binding.etAddress.text.toString(),
            phone = binding.etPhoneNo.text.toString()
        )
    }

    private fun initView() {
        binding.apply {
            with(editProfileArgs.userData) {
                etName.setText(name)
                etAddress.setText(address)
                etPhoneNo.setText(phone)
                etEmail.setText(editProfileArgs.email)
            }
        }
    }

    private fun setOnClickListener() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
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