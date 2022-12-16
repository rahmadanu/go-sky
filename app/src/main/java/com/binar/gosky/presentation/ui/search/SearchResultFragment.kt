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
import com.binar.gosky.presentation.ui.search.adapter.SearchResultAdapter
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchResultViewModel by viewModels()
    private val args: SearchResultFragmentArgs by navArgs()

    private val adapter: SearchResultAdapter by lazy {
        SearchResultAdapter {
            val action = SearchResultFragmentDirections.actionSearchResultFragmentToKonfirmasiTiketFragment(it)
            findNavController().navigate(action)
        }
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

        args.searchTickets.let {
            binding.apply {
                txtFlight.text = getString(R.string.from_to_to, it.from, it.to)
            }
            viewModel.getTickets(
                it.category,
                it.from,
                it.to,
                it.departureTime,
                it.returnTime,
            )
        }
        Log.d("args", args.searchTickets.toString())

        initList()
        observeData()
        setOnClickListener()
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
        viewModel.ticketsResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    adapter.submitList(it.payload)
                    binding.pbLoading.visibility = View.GONE
                    binding.tvNoFlights.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.tvNoFlights.visibility = View.GONE
                }
                is Resource.Error -> {}
                is Resource.Empty -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.tvNoFlights.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}