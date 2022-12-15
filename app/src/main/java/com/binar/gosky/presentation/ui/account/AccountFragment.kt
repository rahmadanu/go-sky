package com.binar.gosky.presentation.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.gosky.data.network.model.auth.user.CurrentUserData
import com.binar.gosky.data.network.model.users.EditUserRequestBody
import com.binar.gosky.databinding.FragmentAccountBinding
import com.binar.gosky.presentation.ui.auth.login.LoginActivity
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels()

    lateinit var userData: EditUserRequestBody
    lateinit var accessToken: String
    lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.apply {
            tvEditProfile.setOnClickListener {
                navigateToEditProfile()
            }
            tvLogOut.setOnClickListener {
                viewModel.setUserLogin(false)
                //viewModel.setUserAccessToken("")
                navigateToLogin()
            }
        }
    }

    private fun navigateToEditProfile() {
        val action = AccountFragmentDirections.actionAccountFragmentToEditProfileFragment(userData, accessToken, email)
        findNavController().navigate(action)
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun observeData() {
        viewModel.getUserAccessToken().observe(viewLifecycleOwner) {
            viewModel.getCurrentUser("Bearer $it")
            accessToken = it
            Log.d("accessToken", it)
        }
        viewModel.currentUserResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.payload?.data?.let { currentUserData -> bindDataIntoForm(currentUserData)
                        Log.d("currentUserData", currentUserData.toString())
                        userData = EditUserRequestBody(
                            address = currentUserData.address,
                            imageId = currentUserData.imageId,
                            imageUrl = currentUserData.imageUrl,
                            name = currentUserData.name,
                            phone = currentUserData.phone
                        )
                        email = currentUserData.email.toString()
                    }
                }
                else -> {}
            }
        }
    }

    private fun bindDataIntoForm(currentUserData: CurrentUserData) {
        binding.apply {
            tvProfileName.text = currentUserData.name
            tvEmail.text = currentUserData.email
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}