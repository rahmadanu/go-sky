package com.binar.gosky.presentation.ui.auth.password

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.gosky.R
import com.binar.gosky.databinding.FragmentForgotPasswordBinding
import com.binar.gosky.presentation.ui.auth.ValidateEmailBottomSheet

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.apply {
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btnConfirm.setOnClickListener{
                if (validateInput()) {
                    showValidateEmailDialog()
                }
            }
        }
    }

    private fun showValidateEmailDialog(
        isCancelable: Boolean = true,

        ) {
        val currentDialog = parentFragmentManager.findFragmentByTag(ValidateEmailBottomSheet::class.java.simpleName)
        if (currentDialog == null) {
            val email = binding.etEmail.text.toString().trim()
            ValidateEmailBottomSheet(email = email, validateState = ValidateEmailBottomSheet.VALIDATE_FORGOT_PASSWORD).apply {

                this.isCancelable = isCancelable
            }.show(parentFragmentManager, ValidateEmailBottomSheet::class.java.simpleName)
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val email = binding.etEmail.text.toString().trim()

        if (email.isEmpty()) {
            isValid = false
            binding.etEmail.error = "Email must not be empty"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false
            binding.etEmail.error = "Invalid email"
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}