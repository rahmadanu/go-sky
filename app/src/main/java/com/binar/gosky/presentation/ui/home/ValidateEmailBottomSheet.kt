package com.binar.gosky.presentation.ui.home

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.binar.gosky.R
import com.binar.gosky.databinding.FragmentValidateEmailBottomSheetBinding
import com.binar.gosky.presentation.ui.auth.register.RegisterViewModel
import com.binar.gosky.wrapper.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ValidateEmailBottomSheet(private val email: String) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentValidateEmailBottomSheetBinding

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentValidateEmailBottomSheetBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setUpEditTextOtp()
        setOnClickListener()
        observeData()
    }

    private fun observeData() {
        registerViewModel.otpResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {

                }
                else -> {}
            }
        }
    }

    private fun setOnClickListener() {
        binding.tvResendCode.setOnClickListener {
            registerViewModel.getOtp(email)
        }
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
}

class GenericKeyEvent internal constructor(private val currentView: EditText, private val previousView: EditText?) : View.OnKeyListener{
    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if(event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.et_otp_1 && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }


}

class GenericTextWatcher internal constructor(private val currentView: View, private val nextView: View?) :
    TextWatcher {
    override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
        val text = editable.toString()
        when (currentView.id) {
            R.id.et_otp_1 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.et_otp_2 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.et_otp_3 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.et_otp_4 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.et_otp_5 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.et_otp_6 -> if (text.length == 1) nextView!!.requestFocus()
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