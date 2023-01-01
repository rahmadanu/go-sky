package com.binar.gosky.presentation.ui.auth.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.gosky.R
import com.binar.gosky.data.network.model.auth.password.NewPasswordRequestBody
import com.binar.gosky.databinding.FragmentNewPasswordBinding
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPasswordFragment : Fragment() {

    private var _binding: FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!

    private val passwordViewModel: PasswordViewModel by viewModels()

    private val args: NewPasswordFragmentArgs by navArgs()

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

        setOnClickListener()
        observeData()
    }

    private fun observeData() {
        passwordViewModel.newPasswordResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    findNavController().navigate(R.id.action_newPasswordFragment_to_loginFragment)
                }
                else -> {}
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnConfirm.setOnClickListener {
            putNewPassword(parseFormIntoEntity())
        }
    }

    private fun putNewPassword(requestBody: NewPasswordRequestBody) {
        passwordViewModel.putNewPassword(requestBody)
    }

    private fun parseFormIntoEntity(): NewPasswordRequestBody {
        return NewPasswordRequestBody(
            newPassword = binding.etPassword.text.toString(),
            otp = args.otp,
            otpToken = args.otpToken
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}