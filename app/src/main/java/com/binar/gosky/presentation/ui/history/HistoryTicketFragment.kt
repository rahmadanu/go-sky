package com.binar.gosky.presentation.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.gosky.R
import com.binar.gosky.databinding.FragmentHistoryTicketBinding
import com.binar.gosky.presentation.ui.history.adapter.HistoryTicketAdapter
import com.binar.gosky.presentation.ui.search.SearchResultFragmentDirections
import com.binar.gosky.presentation.ui.search.adapter.SearchResultAdapter
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryTicketFragment : Fragment() {

    private var _binding: FragmentHistoryTicketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModels()

    private val adapter: HistoryTicketAdapter by lazy {
        HistoryTicketAdapter {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        observeData()
    }

    private fun getTransactionList(accessToken: String) {
        viewModel.getTransactionList(accessToken)
    }

    private fun initList() {
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HistoryTicketFragment.adapter
        }
    }

    private fun observeData() {
        viewModel.getUserAccessToken().observe(viewLifecycleOwner) {
            getTransactionList(getString(R.string.bearer_token, it))
        }
        viewModel.transactionListResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    adapter.submitList(it.data?.data)
                    Log.d("transactionList", it.data?.data.toString())
                }
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}