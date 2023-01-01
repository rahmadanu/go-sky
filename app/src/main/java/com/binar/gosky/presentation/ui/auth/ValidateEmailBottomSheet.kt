package com.binar.gosky.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.gosky.R
import com.binar.gosky.data.network.model.auth.register.RegisterRequestBody
import com.binar.gosky.data.network.model.users.edit.EditEmailUserRequestBody
import com.binar.gosky.databinding.FragmentValidateEmailBottomSheetBinding
import com.binar.gosky.presentation.ui.account.EditProfileViewModel
import com.binar.gosky.presentation.ui.auth.login.LoginViewModel
import com.binar.gosky.presentation.ui.auth.password.ForgotPasswordFragmentDirections
import com.binar.gosky.presentation.ui.auth.register.RegisterViewModel
import com.binar.gosky.presentation.ui.home.HomeActivity
import com.binar.gosky.wrapper.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ValidateEmailBottomSheet(
    private val name: String = "",
    private val password: String = "",
    private val email: String = "",
    private val validateState: String,
    private val accessToken: String = ""
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentValidateEmailBottomSheetBinding

    private val registerViewModel: RegisterViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val editProfileViewModel: EditProfileViewModel by viewModels()

    private var otp = ""
    private var otpToken = ""

    private lateinit var listener: OnRegisterSuccessListener

    fun setListener(listener: OnRegisterSuccessListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentValidateEmailBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setUpEditTextOtp()
        observeData()
        setOnClickListener()
    }

    private fun observeData() {
        registerViewModel.otpResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_LONG).show()
                    it.data?.data?.otpToken?.let { token -> otpToken = token }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
        registerViewModel.postRegisterUserResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.status.equals("success")) {
                        dismiss()
                        Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_LONG).show()
                        loginViewModel.setUserLogin(true)
                        it.data?.data?.accessToken?.let { accessToken -> listener.onRegisterSuccess(accessToken) }
                    }
                    Log.d("registerresponse", it.data?.data?.accessToken.toString())
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
        registerViewModel.getUserAccessToken().observe(viewLifecycleOwner) {
            Log.d("accessTokenValidate", it)
        }
        editProfileViewModel.editUserEmailResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    dismiss()
                    navigateToAccount()
                    Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_LONG).show()
                } is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }

    private fun navigateToAccount() {
        findNavController().navigate(R.id.action_editProfileFragment_to_accountFragment)
    }

    private fun setOnClickListener() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }
        binding.tvResendCode.setOnClickListener {
            registerViewModel.getOtp(email)
        }
        binding.btnVerify.setOnClickListener {
            binding.apply {
                val otp1 = etOtp1.text.toString().trim()
                val otp2 = etOtp2.text.toString().trim()
                val otp3 = etOtp3.text.toString().trim()
                val otp4 = etOtp4.text.toString().trim()
                val otp5 = etOtp5.text.toString().trim()
                val otp6 = etOtp6.text.toString().trim()

                otp = "$otp1$otp2$otp3$otp4$otp5$otp6"
                Log.d("otp", otp)

                when (validateState) {
                    VALIDATE_REGISTER -> {
                        registerUser(name, otp, otpToken, password)
                    }
                    VALIDATE_FORGOT_PASSWORD -> {
                        navigateToNewPassword(otp, otpToken)
                    }
                    VALIDATE_UPDATE_EMAIL -> {
                        updateEmailUser("Bearer $accessToken", otp, otpToken)
                    }
                }
            }
        }
    }

    private fun navigateToNewPassword(otp: String, otpToken: String) {
        val action = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToNewPasswordFragment(otp, otpToken)
        findNavController().navigate(action)
        dismiss()
    }

    private fun registerUser(name: String, otp: String, otpToken: String, password: String) {
        registerViewModel.postRegisterUser(RegisterRequestBody(name, otp, otpToken, password))
        Log.d("register", RegisterRequestBody(name, otp, otpToken, password).toString())
    }

    private fun updateEmailUser(accessToken: String, otp: String, otpToken: String) {
        editProfileViewModel.putUserEmail(accessToken, EditEmailUserRequestBody(otp, otpToken))
        Log.d("updateEmail", EditEmailUserRequestBody(otp, otpToken).toString())
    }

    private fun initView() {
        binding.tvEmail.text = email
        registerViewModel.getOtp(email)
        setUpTimer()
    }

    private fun setUpTimer() {
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millis: Long) {
                binding.tvTimer.text = "(${(millis / 1000)})"
                binding.tvResendCode.isVisible = false
                binding.tvTimer.isVisible = true
            }

            override fun onFinish() {
                binding.tvResendCode.isVisible = true
                binding.tvTimer.isVisible = false
            }

        }
        timer.start()
    }

    private fun setUpEditTextOtp() {
        binding.apply {
            //GenericTextWatcher here works only for moving to next EditText when a number is entered
            //first parameter is the current EditText and second parameter is next EditText
            etOtp1.addTextChangedListener(GenericTextWatcher(etOtp1, etOtp2))
            etOtp2.addTextChangedListener(GenericTextWatcher(etOtp2, etOtp3))
            etOtp3.addTextChangedListener(GenericTextWatcher(etOtp3, etOtp4))
            etOtp4.addTextChangedListener(GenericTextWatcher(etOtp4, etOtp5))
            etOtp5.addTextChangedListener(GenericTextWatcher(etOtp5, etOtp6))
            etOtp6.addTextChangedListener(GenericTextWatcher(etOtp6, null))

            //GenericKeyEvent here works for deleting the element and to switch back to previous EditText
            //first parameter is the current EditText and second parameter is previous EditText
            etOtp1.setOnKeyListener(GenericKeyEvent(etOtp1, null))
            etOtp2.setOnKeyListener(GenericKeyEvent(etOtp2, etOtp1))
            etOtp3.setOnKeyListener(GenericKeyEvent(etOtp3, etOtp2))
            etOtp4.setOnKeyListener(GenericKeyEvent(etOtp4, etOtp3))
            etOtp5.setOnKeyListener(GenericKeyEvent(etOtp5, etOtp4))
            etOtp6.setOnKeyListener(GenericKeyEvent(etOtp6, etOtp5))
        }
    }

    companion object {
        const val VALIDATE_UPDATE_EMAIL = "validate_update_email"
        const val VALIDATE_REGISTER = "validate_register"
        const val VALIDATE_FORGOT_PASSWORD = "validate_forgot_password"
    }
}

interface OnRegisterSuccessListener {
    fun onRegisterSuccess(accessToken: String)
}

class GenericKeyEvent internal constructor(
    private val currentView: EditText,
    private val previousView: EditText?
) : View.OnKeyListener {
    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.et_otp_1 && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }


}

class GenericTextWatcher internal constructor(
    private val currentView: View,
    private val nextView: View?
) :
    TextWatcher {
    override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
        val text = editable.toString()
        when (currentView.id) {
            R.id.et_otp_1 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.et_otp_2 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.et_otp_3 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.et_otp_4 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.et_otp_5 -> if (text.length == 1) nextView!!.requestFocus()
            //R.id.et_otp_6 -> if (text.length == 1)
            //You can use EditText4 same as above to hide the keyboard
        }
    }

    override fun beforeTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) { // TODO Auto-generated method stub
    }

    override fun onTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) { // TODO Auto-generated method stub
    }

}