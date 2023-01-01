package com.binar.gosky.presentation.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.binar.gosky.R
import com.binar.gosky.data.network.model.auth.login.LoginRequestBody
import com.binar.gosky.databinding.FragmentLoginBinding
import com.binar.gosky.presentation.ui.account.AccountViewModel
import com.binar.gosky.presentation.ui.auth.register.RegisterViewModel
import com.binar.gosky.presentation.ui.home.HomeActivity
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val accountViewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        observeData()
    }

    private fun observeData() {
        loginViewModel.postLoginUserResponse.observe(viewLifecycleOwner) {
            binding.pbLogin.isVisible = false
            binding.etEmail.isEnabled = true
            binding.etPassword.isEnabled = true

            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "${it.data?.status}: ${it.data?.message}", Toast.LENGTH_LONG).show()
                    Log.d("loginResponse", it.data.toString())
                    it.data?.data?.accessToken?.let { accessToken -> loginViewModel.setUserAccessToken(accessToken) }
                    //it.data?.data?.accessToken?.let { accessToken -> accountViewModel.getCurrentUser("Bearer $accessToken") }
                    navigateToHome()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
        loginViewModel.loginStatus.observe(viewLifecycleOwner) {
            if (it) {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        loginViewModel.setUserLogin(true)

        val intent = Intent(requireContext(), HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        activity?.finish()
    }


    private fun setOnClickListener() {
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnLogin.setOnClickListener {
            loginUser()
        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    private fun loginUser() {
        if (validateInput()) {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            binding.etEmail.isEnabled = false
            binding.etPassword.isEnabled = false
            binding.pbLogin.isVisible = true
            loginViewModel.postLoginUser(parseFormIntoEntity(email, password))
        }
    }

    private fun parseFormIntoEntity(email: String, password: String): LoginRequestBody {
        return LoginRequestBody(email, password)
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

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
        if (password.length < 5) {
            isValid = false
            Toast.makeText(
                requireContext(),
                "Password should be at least 5 characters",
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