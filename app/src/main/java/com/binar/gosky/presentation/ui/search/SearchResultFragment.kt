package com.binar.gosky.presentation.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.gosky.R
import com.binar.gosky.databinding.FragmentSearchResultBinding
import com.binar.gosky.presentation.ui.account.AccountViewModel
import com.binar.gosky.presentation.ui.search.adapter.SearchResultAdapter
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private val searchResultViewModel: SearchResultViewModel by viewModels()

    private val args: SearchResultFragmentArgs by navArgs()

    private val adapter: SearchResultAdapter by lazy {
        SearchResultAdapter {
            val action = SearchResultFragmentDirections.actionSearchResultFragmentToKonfirmasiTiketFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getTickets()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initList()
        observeData()
        setOnClickListener()
    }

    private fun initView() {
        binding.apply {
            args.searchTickets.let {
                txtFlight.text = getString(R.string.from_to_to, it.from, it.to)
            }
        }
    }

    private fun getTickets() {
        args.searchTickets.let {
            searchResultViewModel.getTickets(
                it.category,
                it.from,
                it.to,
                it.departureTime,
                it.returnTime,
            )
        }
        Log.d("args", args.searchTickets.toString())
    }

    private fun setOnClickListener() {
        binding.apply {
            ivBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun initList() {
        binding.rcvTrip.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchResultFragment.adapter
        }
    }

    private fun observeData() {
        searchResultViewModel.ticketsResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    adapter.submitList(it.payload)
                    binding.pbLoading.visibility = View.GONE
                    binding.tvNoFlights.visibility = View.GONE
                    binding.rcvTrip.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    binding.tvNoFlights.visibility = View.GONE
                    binding.rcvTrip.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is Resource.Error -> {}
                is Resource.Empty -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.tvNoFlights.visibility = View.VISIBLE
                }
            }
        }
        searchResultViewModel.checkIfUserAdmin().observe(viewLifecycleOwner) {
            checkIfUserIsAdmin(it)
        }
    }

    private fun checkIfUserIsAdmin(role: String) {
        adapter.checkIfUserIsAdmin(role == "ADMIN")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}