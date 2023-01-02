package com.binar.gosky.presentation.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.gosky.R
import com.binar.gosky.databinding.FragmentRegisterBinding
import com.binar.gosky.presentation.ui.auth.OnRegisterSuccessListener
import com.binar.gosky.presentation.ui.auth.ValidateEmailBottomSheet
import com.binar.gosky.presentation.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class
RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvLogin.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun registerUser() {
        if (validateInput()) {
            showValidateEmailDialog()
        }
    }

    private fun showValidateEmailDialog(
        isCancelable: Boolean = true,

    ) {
        val currentDialog = parentFragmentManager.findFragmentByTag(ValidateEmailBottomSheet::class.java.simpleName)
        if (currentDialog == null) {
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            ValidateEmailBottomSheet(name, password, email, ValidateEmailBottomSheet.VALIDATE_REGISTER).apply {
                setListener(object : OnRegisterSuccessListener {
                    override fun onRegisterSuccess(accessToken: String) {
                        viewModel.setUserAccessToken(accessToken)
                        navigateToHome()
                    }
                })
                this.isCancelable = isCancelable
            }.show(parentFragmentManager, ValidateEmailBottomSheet::class.java.simpleName)
        }
    }

    private fun navigateToHome() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (name.isEmpty()) {
            isValid = false
            binding.etName.error = "Username or password must not be empty"
        } else
        if (email.isEmpty()) {
            isValid = false
            binding.etEmail.error = "Email must not be empty"
        } else
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false
            binding.etEmail.error = "Invalid email"
        } else
        if (password.isEmpty()) {
            isValid = false
            Toast.makeText(requireContext(), "Password must not be empty", Toast.LENGTH_SHORT)
                .show()
        } else
        if (password.length < 6) {
            isValid = false
            Toast.makeText(
                requireContext(),
                "Password should be at least 6 characters",
                Toast.LENGTH_SHORT
            ).show()
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}