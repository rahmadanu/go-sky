package com.binar.gosky.presentation.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.gosky.R
import com.binar.gosky.data.network.model.auth.password.NewPasswordRequestBody
import com.binar.gosky.data.network.model.users.password.NewPasswordResetRequestBody
import com.binar.gosky.databinding.FragmentNewPasswordBinding
import com.binar.gosky.presentation.ui.auth.password.PasswordViewModel
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    private var _binding: FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!

    private val accountViewModel: AccountViewModel by viewModels()
    private val passwordViewModel: PasswordViewModel by viewModels()

    lateinit var accessToken: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setOnClickListener()
        observeData()
    }

    private fun initView() {
        binding.apply {
            tilCurrentPassword.isVisible = true
        }
    }

    private fun observeData() {
        accountViewModel.getUserAccessToken().observe(viewLifecycleOwner) {
            accessToken = it
        }
        passwordViewModel.newPasswordResetResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    findNavController().navigateUp()
                }
                else -> {}
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnConfirm.setOnClickListener {
            putNewPasswordReset("Bearer $accessToken", parseFormIntoEntity())
        }
    }

    private fun putNewPasswordReset(accessToken: String, requestBody: NewPasswordResetRequestBody) {
        passwordViewModel.putNewPasswordInResetPassword(accessToken, requestBody)
    }

    private fun parseFormIntoEntity(): NewPasswordResetRequestBody {
        return NewPasswordResetRequestBody(
            password = binding.etCurrentPassword.text.toString(),
            newPassword = binding.etNewPassword.text.toString(),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}