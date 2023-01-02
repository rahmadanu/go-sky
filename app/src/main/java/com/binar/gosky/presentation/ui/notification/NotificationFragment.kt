package com.binar.gosky.presentation.ui.notification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.gosky.R
import com.binar.gosky.databinding.FragmentNotificationBinding
import com.binar.gosky.presentation.ui.notification.adapter.NotificationAdapter
import com.binar.gosky.presentation.ui.search.SearchResultFragmentDirections
import com.binar.gosky.presentation.ui.search.adapter.SearchResultAdapter
import com.binar.gosky.wrapper.Resource
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotificationViewModel by viewModels()

    private val adapter: NotificationAdapter by lazy {
        NotificationAdapter{ notificationId ->
            viewModel.putNotificationRead(getString(R.string.bearer_token, accessToken), notificationId)
        }
    }

    private lateinit var accessToken: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initList()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.apply {
            ivBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun observeData() {
        viewModel.getUserAccessToken().observe(viewLifecycleOwner) {
            accessToken = it
            viewModel.getNotification(getString(R.string.bearer_token, it))
        }
        viewModel.notificationResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    setLoading(false)
                    adapter.submitList(it.payload?.data)
                    Log.d("notification", it.payload?.data.toString())
                    if (it.payload?.data.isNullOrEmpty()) {
                        setNotificationEmpty(true)
                    } else {
                        setNotificationEmpty(false)
                    }
                }
                is Resource.Loading -> {
                    setLoading(true)
                }
                else -> {}
            }
        }
    }

    private fun setLoading(isVisible: Boolean) {
        binding.pbLoading.isVisible = isVisible
    }

    private fun setNotificationEmpty(setTrue: Boolean) {
        binding.apply {
            if (setTrue) {
                rvNotification.isVisible = false
                ivGoSkyLogo.isVisible = true
                tvEmptyNotificationTitle.isVisible = true
                tvEmptyNotificationMessage.isVisible = true
            } else {
                rvNotification.isVisible = true
                ivGoSkyLogo.isVisible = false
                tvEmptyNotificationTitle.isVisible = false
                tvEmptyNotificationMessage.isVisible = false
            }
        }
    }

    private fun initList() {
        binding.rvNotification.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@NotificationFragment.adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
